package com.towelove.system.service.mail;


import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailException;
import cn.hutool.extra.mail.MailUtil;
import com.google.common.annotations.VisibleForTesting;
import com.towelove.common.core.enums.CommonStatusEnum;
import com.towelove.common.core.enums.UserTypeEnum;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.system.api.SysUserService;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.convert.mail.MailAccountConvert;
import com.towelove.system.domain.mail.MailAccountDO;
import com.towelove.system.domain.mail.MailTemplateDO;
import com.towelove.system.event.SysUserRegisterEvent;
import com.towelove.system.mapper.user.SysUserMapper;
import com.towelove.system.mq.message.mail.MailSendMessage;
import com.towelove.system.mq.producer.mail.MailProducer;
import lombok.extern.slf4j.Slf4j;
import org.omg.IOP.ServiceContextHolder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 邮箱发送 Service 实现类
 *
 * @author: 张锦标
 * @since 2023-03-01
 */
@Service
@Validated
@Slf4j
public class MailSendServiceImpl implements MailSendService {

    @Resource
    private MailAccountService mailAccountService;
    @Resource
    private MailTemplateService mailTemplateService;

    @Resource
    private MailLogService mailLogService;
    @Resource
    private MailProducer mailProducer;
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    @Deprecated
    public Long sendSingleMailToAdmin(String mail, Long userId, String templateCode,
                                      Map<String, Object> templateParams) {
        if(StringUtils.isEmpty(mail)){
            SysUser sysUser = sysUserMapper.selectById(userId);
            if(Objects.nonNull(sysUser)){
                mail= sysUser.getEmail();
            }
        }
        return sendSingleMail(mail, userId,
                UserTypeEnum.ADMIN.getValue(), templateCode, templateParams);
    }

    @Override
    @Deprecated
    public Long sendSingleMailToUser(String mail, Long userId,
                                       String templateCode, Map<String, Object> templateParams) {
        Integer userType = 2;
        if(StringUtils.isEmpty(mail)){
            SysUser sysUser = sysUserMapper.selectById(userId);
            if(Objects.nonNull(sysUser)){
                mail= sysUser.getEmail();
                userType = Integer.valueOf(sysUser.getUserType());
            }
        }
        return sendSingleMail(mail, userId,
                userType, templateCode, templateParams);

    }

    @Override
    public Long sendSingleMail(String mail, Long userId, Integer userType,
                               String templateCode, Map<String, Object> templateParams) {
        // 校验邮箱模版是否合法
        MailTemplateDO template = validateMailTemplate(templateCode);
        // 校验邮箱账号是否合法
        MailAccountDO account = validateMailAccount(template.getAccountId());
        // 校验邮箱是否存在
        mail = validateMail(mail);
        //检查模板参数是否合法
        validateTemplateParams(template, templateParams);

        // 创建发送日志。如果模板被禁用，则不发送短信，只记录日志
        Boolean isSend = CommonStatusEnum.ENABLE.getStatus().equals(template.getStatus());
        //使用模板的contect字段并且通过页面传递过来的模板参数进行封装
        //<p>您的验证码是{code}，名字是{name}</p>  --- ["code","name"]
        String content = mailTemplateService.
                formatMailTemplateContent(template.getContent(), templateParams);
        Long sendLogId = mailLogService.createMailLog(userId, userType, mail,
                account, template, content, templateParams, isSend);
        // 发送 MQ 消息，异步执行发送短信
        if (isSend) {
            mailProducer.sendMailSendMessage
                    (sendLogId, mail, account.getId(),
                    template.getNickname(), template.getTitle(), content);
        }
        return sendLogId;
    }

    /**
     * 该方法提供给MQ去发送
     * @param message 邮件
     */
    @Override
    public void doSendMail(MailSendMessage message) {
        // 1. 创建发送账号
        MailAccountDO account = validateMailAccount(message.getAccountId());
        MailAccount mailAccount  =
                MailAccountConvert.INSTANCE.convert(account, message.getNickname());
        // 2. 使用hutool发送邮件
        try {
            String messageId = MailUtil.send(mailAccount, message.getMail(),
                    message.getTitle(), message.getContent(),false,null);
            // 3. 更新结果（成功）
            mailLogService.updateMailSendResult(message.getLogId(),
                    messageId, null);
        } catch (Exception e) {
            // 3. 更新结果（异常）
            mailLogService.updateMailSendResult(message.getLogId(),
                    null, e);
        }
    }



    /**
     * 管理员监听用户注册事件
     * 并且告知管理员当前项目的使用人数
     * 后期需要把这个东西删除 不然消息太多哈哈哈
     */
    //TODO 测试通过后注释这个事件
    //@EventListener
    //public void sendRegisterEventToAdmin(SysUserRegisterEvent event){
    //    int persons = mailAccountService.getMailAccountList().size();
    //    SysUser sysUser = event.getSysUser();
    //    String email = sysUser.getEmail();
    //    MailAccount mailAccount = new MailAccount()
    //            .setFrom("Towelove官方<460219753@qq.com>") // 邮箱地址
    //            .setHost("smtp.qq.com").setPort(465).setSslEnable(true) // SMTP 服务器
    //            .setAuth(true).setUser("460219753@qq.com").setPass("xxayxcbswxorbggb"); // 登录账号密码
    //    String messageId = MailUtil.send(mailAccount, "460219753@qq.com",
    //            "Towelove有新用户啦", "又有一位新用户进行了注册，" +
    //                    "当前项目用户数："+persons, false);
    //}
    @VisibleForTesting
    MailTemplateDO validateMailTemplate(String templateCode) {
        // 获得邮件模板。考虑到效率，从缓存中获取
        MailTemplateDO template = mailTemplateService.getMailTemplateByCodeFromCache(templateCode);
        // 邮件模板不存在
        if (template == null) {
            throw new MailException("当前邮件模板不存在");
        }
        return template;
    }

    @VisibleForTesting
    MailAccountDO validateMailAccount(Long accountId) {
        // 获得邮箱账号。考虑到效率，从缓存中获取
        MailAccountDO account = mailAccountService.getMailAccountFromCache(accountId);
        // 邮箱账号不存在
        if (account == null) {
            throw new MailException("当前账户id:"+accountId+"不存在");
        }
        return account;
    }

    @VisibleForTesting
    String validateMail(String mail) {
        if (StrUtil.isEmpty(mail)) {
            throw new MailException("当前邮件:"+mail+"不存在");
        }
        return mail;
    }

    /**
     * 校验邮件参数是否确实
     *
     * @param template 邮箱模板
     * @param templateParams 参数列表
     */
    @VisibleForTesting
    void validateTemplateParams(MailTemplateDO template, Map<String, Object> templateParams) {
        template.getParams().forEach((key) -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw new MailException("当前参数键:"+key+"对应值不存在");
            }
        });
    }


}
