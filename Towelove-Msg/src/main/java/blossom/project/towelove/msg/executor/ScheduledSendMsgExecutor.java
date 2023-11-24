package blossom.project.towelove.msg.executor;

import blossom.project.towelove.client.serivce.RemoteUserService;
import blossom.project.towelove.common.constant.MsgTaskConstants;
import blossom.project.towelove.common.response.mailaccount.MailAccountResponse;
import blossom.project.towelove.msg.cache.TaskCache;
import blossom.project.towelove.msg.entity.CompletedMailMsgTask;
import blossom.project.towelove.msg.entity.MsgTask;
import blossom.project.towelove.msg.entity.OfficialMailInfo;
import blossom.project.towelove.msg.service.EmailService;
import blossom.project.towelove.msg.service.MsgTaskService;
import cn.hutool.extra.mail.MailException;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/22 15:12
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * ScheduledSendMsgExecutor类的作用是获取数据库中消息
 * 并且定时进行发送
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledSendMsgExecutor {

    private final OfficialMailInfo officialMailInfo;

    private final RemoteUserService remoteUserService;

    private final MsgTaskService msgTaskService;

    private final EmailService emailService;
    /**
     * 任务模块需要定时的去获取数据库中的用户消息任务
     * 那么这个模块就需要要求能远程调用和发送邮件相关的所有函数
     * 然后我们根据hashmap中的key和value在暂时缓存任务
     * 比如key代表的是任务的id，id格式为：msg：id，
     * 这样子就可以确保消息在被要发送钱被更新了，就可以取出任务并且更新任务
     * 那么此时就需要在sys创建一个消息更新和插入模块的producer，
     * 然后在xxl模块去监听这个消息，然后读取消息再次放入到我们的mq中
     */
    //任务初始化方法
    public void initHandler() {
        log.info("task init ...");
        System.out.println("任务调用初始化方法执行");
    }

    public void destroyHandler() {
        log.info("task destory ... ");
        System.out.println("任务执行器被销毁");
    }


    /**
     * @author: 张锦标
     * 当前方法用于获取每天都发送的消息
     * 将每十分钟的任务暂存到map中
     * 之后到快要发送消息的时候
     * 再将消息推送到mq准备发送
     */
    @XxlJob(value = "sendMailEveryDayJobHandler", init = "initHandler", destroy = "destroyHandler")
    public void sendMailEveryDay() {
        try {
            //拿到十分钟后要发送的数据
            List<MsgTask> msgTaskList = msgTaskService.getMsgTaskList(0);
            System.out.println(msgTaskList);
            for (MsgTask msgTask : msgTaskList) {
                CompletedMailMsgTask completeMsg = new CompletedMailMsgTask();
                BeanUtils.copyProperties(msgTask, completeMsg);
                MailAccountResponse mailAccount = new MailAccountResponse();
                //MailAccountResponse mailAccount = remoteUserService.
                //        getMailAccount(msgTask.getAccountId()).getData();
                if (Objects.nonNull(mailAccount)) {
                    //BeanUtils.copyProperties(mailAccount, completeMsg);
                    BeanUtils.copyProperties(officialMailInfo,completeMsg);
                    BeanUtils.copyProperties(msgTask, completeMsg);
                    //将所有的任务放入到map中暂存
                    //TODO 这里需要考虑如果用户更新了邮箱信息 那么这些东西都要失效
                    //所以要去cache中查找是否已经缓存了记录
                    TaskCache.getTaskMap().put(MsgTaskConstants.MSG_PREFIX + completeMsg.getId(), completeMsg);
                } else {
                    throw new MailException("邮箱账户为空，出现异常！！！");
                }
            }
            //TODO 多线程性能优化 需要考虑ForkJoinPool是公共线程池
            //msgTaskList.parallelStream().peek(msgTask -> {
            //    CompletedMailMsgTask msg = new CompletedMailMsgTask();
            //    BeanUtils.copyProperties(msgTask, msg);
            //    MailAccountRespVO mailAccount = remoteSysMailAccountService.
            //            getMailAccount(msgTask.getAccountId()).getData();
            //    if (Objects.nonNull(mailAccount)) {
            //        BeanUtils.copyProperties(mailAccount, msg);
            //        BeanUtils.copyProperties(msgTask, msg);
            //        //将所有的任务放入到map中暂存
            //        TaskCache.getTaskMap().put(MsgTaskConstants.MSG_PREFIX + msg.getId(), msg);
            //    } else {
            //        throw new MailException("邮箱账户为空，出现异常！！！");
            //    }
            //});

            //将获得到的消息任务绑定到mq队列中
        }catch (Exception e){

        }finally {
            //将获得到的消息任务绑定mq队列中
            for (Map.Entry<String, CompletedMailMsgTask> entry : TaskCache.getTaskMap().entrySet()) {
                CompletedMailMsgTask CompletedMailMsgTask = entry.getValue();
                System.out.println(CompletedMailMsgTask);
                emailService.sendCompletedMailMsg(CompletedMailMsgTask);
            }
            //完成把消息放入到RocketMQ之后就需要清空一下Map集合了
            TaskCache.getTaskMap().clear();
            System.out.println("发送邮件给MQ成功");
        }
    }

    /**
     * @author: 张锦标
     * 当前方法用于获取今天的且只发送一次的消息
     * 将每十分钟的任务暂存到map中
     * 之后到快要发送消息的时候
     * 再将消息推送到mq准备发送
     */
    @XxlJob(value = "sendMailOnceJobHandler", init = "initHandler", destroy = "destroyHandler")
    public void sendMailOnce() {
        try {
            //拿到十分钟后要发送的数据
            List<MsgTask> msgTaskList = msgTaskService.getMsgTaskList(1);
            System.out.println(msgTaskList);
            for (MsgTask msgTask : msgTaskList) {
                CompletedMailMsgTask completeMsg = new CompletedMailMsgTask();
                BeanUtils.copyProperties(msgTask, completeMsg);
                MailAccountResponse mailAccount = new MailAccountResponse();
                //MailAccountResponse mailAccount = remoteUserService.
                //        getMailAccount(msgTask.getAccountId()).getData();
                if (Objects.nonNull(mailAccount)) {
                    //BeanUtils.copyProperties(mailAccount, completeMsg);
                    BeanUtils.copyProperties(officialMailInfo,completeMsg);
                    BeanUtils.copyProperties(msgTask, completeMsg);
                    //将所有的任务放入到map中暂存
                    //TODO 这里需要考虑如果用户更新了邮箱信息 那么这些东西都要失效
                    //所以要去cache中查找是否已经缓存了记录
                    TaskCache.getTaskMap().put(MsgTaskConstants.MSG_PREFIX + completeMsg.getId(), completeMsg);
                } else {
                    throw new MailException("邮箱账户为空，出现异常！！！");
                }
            }
            //TODO 多线程性能优化 需要考虑ForkJoinPool是公共线程池
            //msgTaskList.parallelStream().peek(msgTask -> {
            //    CompletedMailMsgTask msg = new CompletedMailMsgTask();
            //    BeanUtils.copyProperties(msgTask, msg);
            //    MailAccountRespVO mailAccount = remoteSysMailAccountService.
            //            getMailAccount(msgTask.getAccountId()).getData();
            //    if (Objects.nonNull(mailAccount)) {
            //        BeanUtils.copyProperties(mailAccount, msg);
            //        BeanUtils.copyProperties(msgTask, msg);
            //        //将所有的任务放入到map中暂存
            //        TaskCache.getTaskMap().put(MsgTaskConstants.MSG_PREFIX + msg.getId(), msg);
            //    } else {
            //        throw new MailException("邮箱账户为空，出现异常！！！");
            //    }
            //});

            //将获得到的消息任务绑定到mq队列中
        }catch (Exception e){

        }finally {
            //将获得到的消息任务绑定mq队列中
            for (Map.Entry<String, CompletedMailMsgTask> entry : TaskCache.getTaskMap().entrySet()) {
                CompletedMailMsgTask CompletedMailMsgTask = entry.getValue();
                System.out.println(CompletedMailMsgTask);
                emailService.sendCompletedMailMsg(CompletedMailMsgTask);
            }
            //完成把消息放入到RocketMQ之后就需要清空一下Map集合了
            TaskCache.getTaskMap().clear();
            System.out.println("发送邮件给MQ成功");
        }
    }

}
