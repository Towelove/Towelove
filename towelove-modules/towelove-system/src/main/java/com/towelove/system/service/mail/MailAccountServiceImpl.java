package com.towelove.system.service.mail;


import cn.hutool.extra.mail.MailException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.towelove.common.async.config.AsyncConfig;
import com.towelove.common.core.constant.RedisServiceConstants;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.redis.service.RedisService;
import com.towelove.system.api.factory.SysMailAccountFallbackFactory;
import com.towelove.system.convert.mail.MailAccountConvert;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.vo.account.MailAccountCreateReqVO;
import com.towelove.system.domain.mail.vo.account.MailAccountPageReqVO;
import com.towelove.system.domain.mail.vo.account.MailAccountUpdateReqVO;
import com.towelove.system.mapper.mail.MailAccountMapper;
import com.towelove.system.mq.producer.mail.MailProducer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.towelove.common.core.utils.CollectionUtils.convertMap;

/**
 * 邮箱账号 Service 实现类
 *
 * @author: 张锦标
 * @since 2023-03-11
 */
@Service
@Validated
@Slf4j
public class MailAccountServiceImpl implements MailAccountService {
    private static final Logger logger = LoggerFactory.getLogger(MailAccountServiceImpl.class);
    @Resource
    private MailAccountMapper mailAccountMapper;

    @Resource
    private MailTemplateService mailTemplateService;

    @Resource
    private MailProducer mailProducer;
    @Resource
    private RedisService redisService;
    //对于实现了Future接口的类或接口 其异常处理必须通过回调
    //而不是全局异步异常处理器
    @Async(AsyncConfig.CPU_INTENSIVE)
    public ListenableFuture<Integer> cpuIntensiveExector() {
        try {
            //throw new RuntimeException("报个错吧");
            return AsyncResult.forValue(1);
        } catch (Throwable ex) {
            return AsyncResult.forExecutionException(ex);
        }
    }

    @Async(AsyncConfig.IO_INTENSIVE)
    public ListenableFuture<Integer> ioIntensiveExector() {
        try {
            throw new RuntimeException("报个错吧");
            //return AsyncResult.forValue(2);
        } catch (Throwable ex) {
            return AsyncResult.forExecutionException(ex);
        }
    }

    @Async
    public Integer exectorWithWrong() {
        throw new RuntimeException("报个错吧!!!然后被全局异步异常处理器处理");
    }

    public void testAsyncWithCallBack() throws ExecutionException, InterruptedException {
        ListenableFuture<Integer> ioResult = ioIntensiveExector();
        ListenableFuture<Integer> cpuResult = cpuIntensiveExector();
        Integer integer = exectorWithWrong();
        System.out.println(integer);
        // 增加成功和失败的统一回调
        ioResult.addCallback(new ListenableFutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                logger.info("[onSuccess][result: {}]", result);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("[onFailure][发生异常]", ex);
            }
        });
        cpuResult.addCallback(new ListenableFutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                logger.info("[onSuccess][result: {}]", result);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("[onFailure][发生异常]", ex);
            }
        });
        //再阻塞获取计算结果之后
        //将会调用上面配置的回调方法
        ioResult.get();
        cpuResult.get();
    }

    public void testAsync() {
        ioIntensiveExector();
        cpuIntensiveExector();
    }

    /**
     * 邮箱账号缓存 创建的是本地缓存 使用的不是redis
     * 后期可以换为redis
     * key：邮箱账号编码 {@link MailAccountDO#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile Map<Long, MailAccountDO> mailAccountCache;

    @Override
    @PostConstruct //当前类构造后就会执行当前方法
    public void initLocalCache() {
        // 第一步：查询所有的数据（可以优化 不应该使用jvm内存来做缓存）
        List<MailAccountDO> accounts = mailAccountMapper.selectList();
        log.info("[initLocalCache][缓存邮箱账号，数量:{}]", accounts.size());
        // 第二步：构建缓存 也就是把所有的数据库里面查询出来的id放在我们的代码中
        mailAccountCache = convertMap(accounts, MailAccountDO::getId);
    }

    @Override
    public MailAccountDO getMailAccountFromCache(Long id) {
        return mailAccountCache.get(id);
    }

    @Override
    public Long createMailAccount(MailAccountCreateReqVO createReqVO) {
        // 插入
        MailAccountDO account = MailAccountConvert.INSTANCE.convert(createReqVO);
        mailAccountMapper.insert(account);

        // 发送刷新消息
        //mailProducer.sendMailAccountRefreshMessage();
        return account.getId();
    }

    public static final int WAIT_SIZE = 10;
    private static List<MailAccountDO> waitList = new ArrayList<>();

    //TODO 使用Write Behind方式来保证数据库与缓存的数据一致性
    private void writeBehindUpdateMailAccount(MailAccountUpdateReqVO updateReqVO) {
        // 校验是否存在
        validateMailAccountExists(updateReqVO.getId());
        MailAccountDO updateObj = MailAccountConvert.INSTANCE.convert(updateReqVO);
        //先更新更新缓存
        redisService.setCacheObject(RedisServiceConstants.SYS_MAIL_ACCOUNT + updateReqVO.getId(), updateObj);
        //先把要更新的数据放到一个队列中 之后批量更新
        waitList.add(updateObj);
        //TODO 使用全局线程池去管理这个任务
        if (waitList.size() == WAIT_SIZE) {
            new Thread(() -> {
                //批量执行任务
                mailAccountMapper.updateBatch(waitList, WAIT_SIZE);
                //清空任务
                waitList.clear();
            }).start();
        }
    }

    @Override
    public void updateMailAccount(MailAccountUpdateReqVO updateReqVO) {
        // 校验是否存在
        validateMailAccountExists(updateReqVO.getId());

        // 更新
        MailAccountDO updateObj = MailAccountConvert.INSTANCE.convert(updateReqVO);
        mailAccountMapper.updateById(updateObj);
        // 发送刷新消息
        //mailProducer.sendMailAccountRefreshMessage();
        //更新缓存
        redisService.setCacheObject(RedisServiceConstants.SYS_MAIL_ACCOUNT + updateReqVO.getId(), updateObj);
    }

    @Override
    public MailAccountDO getMailAccount(Long id) {
        return mailAccountMapper.selectById(id);
    }

    @Override
    public PageResult<MailAccountDO> getMailAccountPage(MailAccountPageReqVO pageReqVO) {
        return mailAccountMapper.selectPage(pageReqVO);
    }

    //TODO 一个人有没有可能它可以有多个邮箱。。。
    @Override
    public MailAccountDO getMailAccountByUserId(Long userId) {
        LambdaQueryWrapper<MailAccountDO> lqw = new LambdaQueryWrapper();
        lqw.eq(MailAccountDO::getUserId, userId);
        return mailAccountMapper.selectOne(lqw);
    }

    @Override
    public List<MailAccountDO> getMailAccountList() {
        return mailAccountMapper.selectList();
    }

    @Override
    public void deleteMailAccount(Long id) {
        // 校验是否存在账号
        validateMailAccountExists(id);
        // 校验是否存在关联模版
        if (mailTemplateService.countByAccountId(id) > 0) {
            throw new MailException("无法删除，该邮箱账号还有邮件模板");
        }

        // 删除
        mailAccountMapper.deleteById(id);
        // 发送刷新消息
        //mailProducer.sendMailAccountRefreshMessage();
    }

    private void validateMailAccountExists(Long id) {
        if (mailAccountMapper.selectById(id) == null) {
            throw new MailException("邮箱账号不存在");
        }
    }
}
