//package blossom.project.towelove.taskcenter.executor;
//
//import blossom.project.towelove.client.serivce.RemoteMsgService;
//import blossom.project.towelove.client.serivce.RemoteUserService;
//import cn.hutool.extra.mail.MailException;
//import com.towelove.common.core.constant.MsgTaskConstants;
//import com.towelove.common.core.constant.SecurityConstants;
//import com.towelove.common.core.domain.MailSendMessage;
//import com.towelove.common.core.domain.R;
//import com.towelove.common.core.utils.bean.BeanUtils;
//import com.towelove.msg.task.config.TaskMapUtil;
//import com.towelove.msg.task.domain.MailMsg;
//import com.towelove.msg.task.domain.MsgTask;
//import com.towelove.msg.task.domain.vo.MsgTaskSimpleRespVO;
//import com.towelove.msg.task.mq.producer.MailMessageProducer;
//import com.towelove.msg.task.mq.producer.SysMessageProducer;
//import com.towelove.msg.task.service.MsgTaskService;
//import com.towelove.system.api.RemoteSysMailAccountService;
//import com.towelove.system.api.RemoteSysUserService;
//import com.towelove.system.api.model.LoginUser;
//import com.towelove.system.api.model.MailAccountRespVO;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Objects;
//
///**
// * @author: ZhangBlossom
// * @date: 2023/11/22 15:12
// * @contact: QQ:4602197553
// * @contact: WX:qczjhczs0114
// * @blog: https://blog.csdn.net/Zhangsama1
// * @github: https://github.com/ZhangBlossom
// * ScheduledSendMsgExecutor类的作用是获取数据库中消息
// * 并且定时进行发送
// */
//@Component
//@RequiredArgsConstructor
//public class ScheduledSendMsgExecutor {
//
//    @Autowired
//    private final RemoteUserService remoteUserService;
//
//    @Autowired
//    private final RemoteMsgService remoteMsgService;
//
//    //日志记录  `
//    /**
//     * 任务模块需要定时的去获取数据库中的用户消息任务
//     * 那么这个模块就需要要求能远程调用和发送邮件相关的所有函数
//     * 然后我们根据hashmap中的key和value在暂时缓存任务
//     * 比如key代表的是任务的id，id格式为：msg：id，
//     * 这样子就可以确保消息在被要发送钱被更新了，就可以取出任务并且更新任务
//     * 那么此时就需要在sys创建一个消息更新和插入模块的producer，
//     * 然后在xxl模块去监听这个消息，然后读取消息再次放入到我们的mq中
//     */
//    private static final Logger logger = LoggerFactory.getLogger(ScheduledSendMsgExecutor.class);
//    //使用XXLJOB注解定义一个job
//    @XxlJob(value = "myJobHander", init = "initHandler", destroy = "destroyHandler")
//    public void myJobHander() {
//        //做查询数据库操作
//        //使用远程调用方法
//        try {
//            R<LoginUser> userResult = remoteSysUserService
//                    .getUserInfo("张锦标", SecurityConstants.INNER);
//            if (Objects.isNull(userResult)) {
//                //自定义返回给调度中心的失败原因
//                XxlJobHelper.handleFail("任务执行失败，请检查用户模块服务器");
//            }
//            System.out.println(userResult.getData().getSysUser().getUserName());
//            //这个方法底层是给HandlerContext设置返回值以及消息
//            XxlJobHelper.handleSuccess("任务执行成功");
//        } catch (Exception e) {
//            //在控制台终止程序的时候是使用InterruptException异常来停止，我们需要对这个异常进行抛出，否则无法停止任务
//            if (e instanceof InterruptedException) {
//                throw e;
//            }
//            logger.error("{}", e);
//        }
//    }
//
//    //任务初始化方法
//    public void initHandler() {
//        logger.info("task init ...");
//        System.out.println("任务调用初始化方法执行");
//    }
//
//    public void destroyHandler() {
//        System.out.println("任务执行器被销毁");
//    }
//
//    @Autowired
//    private RemoteSysMailAccountService remoteSysMailAccountService;
//
//    /**
//     * @author: 张锦标
//     * 将每十分钟的任务暂存到map中
//     * 之后到快要发送消息的时候
//     * 再将消息推送到mq准备发送
//     */
//    //@XxlJob(value = "TaskJobHandler", init = "initHandler", destroy = "destroyHandler")
//    //public void getTaskFromDB() {
//    //    //拿到十分钟后要发送的数据
//    //    List<MsgTask> msgTaskList = msgTaskService.getMsgTaskList();
//    //    System.out.println(msgTaskList);
//    //    for (MsgTask msgTask : msgTaskList) {
//    //        MailMsg msg = new MailMsg();
//    //        BeanUtils.copyProperties(msgTask, msg);
//    //        MailAccountRespVO mailAccount = remoteSysMailAccountService.
//    //                getMailAccount(msgTask.getAccountId()).getData();
//    //        if (Objects.nonNull(mailAccount)) {
//    //            BeanUtils.copyProperties(mailAccount, msg);
//    //            //BeanUtils.copyProperties(msgTask, msg);
//    //            //将所有的任务放入到map中暂存
//    //            TaskMapUtil.getTaskMap().put(MsgTaskConstants.MSG_PREFIX + msg.getId(), msg);
//    //        } else {
//    //            throw new MailException("邮箱账户为空，出现异常！！！");
//    //        }
//    //    }
//    //    //msgTaskList.parallelStream().peek(msgTask -> {
//    //    //    MailMsg msg = new MailMsg();
//    //    //    BeanUtils.copyProperties(msgTask, msg);
//    //    //    MailAccountRespVO mailAccount = remoteSysMailAccountService.
//    //    //            getMailAccount(msgTask.getAccountId()).getData();
//    //    //    if (Objects.nonNull(mailAccount)) {
//    //    //        BeanUtils.copyProperties(mailAccount, msg);
//    //    //        BeanUtils.copyProperties(msgTask, msg);
//    //    //        //将所有的任务放入到map中暂存
//    //    //        TaskMapUtil.getTaskMap().put(MsgTaskConstants.MSG_PREFIX + msg.getId(), msg);
//    //    //    } else {
//    //    //        throw new MailException("邮箱账户为空，出现异常！！！");
//    //    //    }
//    //    //});
//    //
//    //    //将获得到的消息任务绑定到mq队列中
//    //}
//
//    @XxlJob(value = "SendMailJobHandler", init = "initHandler", destroy = "destroyHandler")
//    public void SendMailJobHandler() {
//
//        //拿到十分钟后要发送的数据
//        List<MsgTask> msgTaskList = msgTaskService.getMsgTaskList();
//        System.out.println(msgTaskList);
//        for (MsgTask msgTask : msgTaskList) {
//            MailMsg msg = new MailMsg();
//            BeanUtils.copyProperties(msgTask, msg);
//            MailAccountRespVO mailAccount = remoteSysMailAccountService.
//                    getMailAccount(msgTask.getAccountId()).getData();
//            if (Objects.nonNull(mailAccount)) {
//                BeanUtils.copyProperties(mailAccount, msg);
//                //BeanUtils.copyProperties(msgTask, msg);
//                //将所有的任务放入到map中暂存
//
//                mailMessageProducer.sendMailMessage(msg);
//
//                //TaskMapUtil.getTaskMap().put(MsgTaskConstants.MSG_PREFIX + msg.getId(), msg);
//            } else {
//                throw new MailException("邮箱账户为空，出现异常！！！");
//            }
//        }
//
//        //将获得到的消息任务绑定mq队列中
//        //for (Map.Entry<String, MailMsg> entry : TaskMapUtil.getTaskMap().entrySet()) {
//        //    MailMsg mailMsg = entry.getValue();
//        //    System.out.println(mailMsg);
//        //    mailMessageProducer.sendMailMessage(mailMsg);
//        //}
//        ////完成把消息放入到RocketMQ之后就需要清空一下Map集合了
//        //TaskMapUtil.getTaskMap().clear();
//        //System.out.println("发送邮件给MQ成功");
//    }
//}
