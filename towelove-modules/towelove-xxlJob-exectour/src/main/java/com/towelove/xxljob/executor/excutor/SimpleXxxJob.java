package com.towelove.xxljob.executor.excutor;

import com.towelove.common.core.constant.SecurityConstants;
import com.towelove.common.core.domain.R;
import com.towelove.system.api.SysUserService;
import com.towelove.system.api.model.LoginUser;
import com.towelove.xxljob.executor.mq.producer.SysMessageProducer;
import com.towelove.xxljob.executor.mq.producer.TaskMessageProducer;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author 季台星
 * @version 1.0
 */
@Component
public class SimpleXxxJob {
    @Autowired(required = false)
    private SysUserService userService;
    //系统任务生产者
    @Autowired
    private SysMessageProducer sysMessageProducer;
    //用户定时消息任务生产者
    @Autowired
    private TaskMessageProducer taskMessageProducer;
    //日志记录
    private static final Logger logger = LoggerFactory.getLogger(SimpleXxxJob.class);
    @XxlJob(value = "myJobHander",init = "initHandler",destroy = "destroyHandler")
    public void myJobHander(){
        //做查询数据库操作
        //使用远程调用方法
        try{
            R<LoginUser> userResult = userService.getUserInfo("季台星", SecurityConstants.INNER);
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
}
