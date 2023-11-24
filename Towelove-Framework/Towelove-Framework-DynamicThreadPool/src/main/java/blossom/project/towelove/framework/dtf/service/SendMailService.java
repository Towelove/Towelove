package blossom.project.towelove.framework.dtf.service;

import blossom.project.towelove.common.constant.MsgConstant;
import blossom.project.towelove.common.entity.msg.OfficialMailInfo;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailException;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * @author: 张锦标
 * @date: 2023/6/15 15:24
 * SendMailToWarn类
 */
@AutoConfiguration
public class SendMailService {

    @Autowired
    private OfficialMailInfo officialMailInfo;
    //@Autowired
    //private JavaMailSender javaMailSender;
    //
    //@Value("${warn.recipient}")
    //public String recipient;
    //
    //@Value("${warn.addresser}")
    //public String addresser;
    //
    //
    //public boolean sendMailToWarn(ThreadPoolExecutor threadPoolExecutor){
    //    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    //    simpleMailMessage.setTo(recipient);
    //    simpleMailMessage.setFrom(addresser);
    //    simpleMailMessage.setSubject("线程池情况汇报");
    //    String s = "CorePoolSize="+threadPoolExecutor.getCorePoolSize()+"   "+
    //            "LargestPoolSize="+threadPoolExecutor.getLargestPoolSize()+"   "+
    //            "MaximumPoolSize="+threadPoolExecutor.getMaximumPoolSize();
    //    simpleMailMessage.setText(s);
    //    javaMailSender.send(simpleMailMessage);
    //    return true;
    //}


    public void sendThreadPoolChangeMail(String content) {
        if (StringUtils.isBlank(officialMailInfo.getUsername())){
            throw new MailException("从nacos配置中心获取的email-username为空...");
        }

        String from = MsgConstant.TOWELOVE_OFFICIAL_MSG + " <" +officialMailInfo.getUsername() + ">";
        //配置MailAccount对象 hutool提供的
        MailAccount mailAccount =
                new MailAccount()
                        .setFrom(from)
                        .setAuth(true)
                        .setUser(officialMailInfo.getUsername())
                        .setPass(officialMailInfo.getPassword())
                        .setHost(officialMailInfo.getHost())
                        .setPort(officialMailInfo.getPort())
                        .setSslEnable(officialMailInfo.getSslEnable());
        //发送邮件 msgIG为邮件id
        try {
            MailUtil.send(mailAccount, officialMailInfo.getUsername(),
                    MsgConstant.THREAD_POOL_CHANGE, content, false,
                    null);
        } catch (Exception e) {
            //远程调用日志记录
            //TODO 这里可以配合线程池
            //TODO 发送失败应该发送一条消息给MQ进行补偿
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
