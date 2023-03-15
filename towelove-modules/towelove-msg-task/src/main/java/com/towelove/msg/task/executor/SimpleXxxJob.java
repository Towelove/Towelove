package com.towelove.msg.task.executor;

import com.towelove.common.core.constant.SecurityConstants;
import com.towelove.common.core.domain.MailSendMessage;
import com.towelove.common.core.domain.R;
import com.towelove.msg.task.domain.vo.MsgTaskSimpleRespVO;
import com.towelove.msg.task.mq.producer.SysMessageProducer;
import com.towelove.msg.task.mq.producer.TaskMessageProducer;
import com.towelove.msg.task.service.MsgTaskService;
import com.towelove.system.api.RemoteSysUserService;
import com.towelove.system.api.model.LoginUser;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author 季台星
 * @version 1.0
 */
@Component
public class SimpleXxxJob {
    private static HashMap<String, MailSendMessage> map;

    public static HashMap<String, MailSendMessage> getMap() {
        return map;
    }

    public static void setMap(HashMap<String, MailSendMessage> map) {
        SimpleXxxJob.map = map;
    }

    @Autowired
    private RemoteSysUserService remoteSysUserService;
    //系统任务生产者
    @Autowired
    private SysMessageProducer sysMessageProducer;
    //用户定时消息任务生产者
    @Autowired
    private TaskMessageProducer taskMessageProducer;
    @Autowired
    private MsgTaskService msgTaskService;
    //日志记录  `
    /**
     * 任务模块需要定时的去获取数据库中的用户消息任务
     * 那么这个模块就需要要求能远程调用和发送邮件相关的所有函数
     * 然后我们根据hashmap中的key和value在暂时缓存任务
     * 比如key代表的是任务的id，id格式为：msg：id，
     * 这样子就可以确保消息在被要发送钱被更新了，就可以取出任务并且更新任务
     * 那么此时就需要在sys创建一个消息更新和插入模块的producer，
     * 然后在xxl模块去监听这个消息，然后读取消息再次放入到我们的mq中
     */
    private static final Logger logger = LoggerFactory.getLogger(SimpleXxxJob.class);
    @XxlJob(value = "myJobHander",init = "initHandler",destroy = "destroyHandler")
    public void myJobHander(){
        //做查询数据库操作
        //使用远程调用方法
        try{
            R<LoginUser> userResult = remoteSysUserService.getUserInfo("季台星", SecurityConstants.INNER);
            if (!Objects.isNull(userResult)){
                //自定义返回给调度中心的失败原因
                XxlJobHelper.handleFail("任务执行失败，请检查用户模块服务器");
            }
            System.out.println(userResult.getData().getSysUser().getUserName());
            //这个方法底层是给HandlerContext设置返回值以及消息
            XxlJobHelper.handleSuccess("任务执行成功");
        }catch (Exception e){
            //在控制台终止程序的时候是使用InterruptException异常来停止，我们需要对这个异常进行抛出，否则无法停止任务
            if (e instanceof InterruptedException){
                throw e;
            }
            logger.error("{}", e);
        }
    }
    //任务初始化方法
    public void initHandler(){
        logger.info("task init ...");
        System.out.println("任务调用初始化方法执行");
    }
    public void destroyHandler(){
        System.out.println("任务执行器被销毁");
    }

    /**
     *
     */
    @XxlJob(value = "TaskJobHandler",init = "initHandler",destroy = "destroyHandler")
    public void getTaskForDB(){

        List<MsgTaskSimpleRespVO> list
                = msgTaskService.getSimpleMailAccountList();
        //将获得到的消息任务绑定到mq队列中
    }
}
