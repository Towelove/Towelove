package com.towelove.xxljob.executor.excutor;

import com.towelove.common.core.constant.SecurityConstants;
import com.towelove.common.core.domain.R;
import com.towelove.system.api.SysUserService;
import com.towelove.system.api.model.LoginUser;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 季台星
 * @version 1.0
 */
@Component
public class SimpleXxxJob {
    @Autowired(required = false)
    private SysUserService userService;
    @XxlJob("myJobHander")
    public void myJobHander(){
        //做查询数据库操作
        //使用远程调用方法
        R<LoginUser> userResult = userService.getUserInfo("季台星",
                SecurityConstants.INNER);
        System.out.println(userResult);

    }
}
