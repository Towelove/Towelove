package com.towelove.auth.service;


import com.towelove.auth.form.LoginBody;
import com.towelove.common.core.constant.SecurityConstants;
import com.towelove.common.core.constant.UserConstants;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.enums.UserStatus;
import com.towelove.common.core.exception.ServiceException;
import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.core.web.domain.AjaxResult;
import com.towelove.common.security.utils.SecurityUtils;
import com.towelove.system.api.RemoteSysUserService;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 登录校验方法
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
@Component
public class SysLoginService
{
    @Autowired
    private RemoteSysUserService remoteSysUserService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private RedisTemplate redisTemplate;

    //@Autowired
    //private SysRecordLogService recordLogService;

    /**
     * 登录
     */
    public LoginUser login(String username, String password)
    {

        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        // 查询用户信息
        R<LoginUser> userResult = remoteSysUserService.getUserInfo(username,
                SecurityConstants.INNER);

        if (Objects.isNull(userResult) || Objects.isNull(userResult.getData()))
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "登录用户不存在");
            throw new ServiceException("登录用户：" + username + " 不存在");
        }

        if (R.FAIL == userResult.getCode())
        {
            throw new ServiceException(userResult.getMsg());
        }
        
        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        //做密码校验，以及是否输入密码次数过多被限制登入
        passwordService.validate(user, password);
        //recordLogService.recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
        //从redis中获得用户缓存信息，防止重复登入
        Boolean hasKey = redisTemplate.hasKey("username:"+userInfo.getUserid()+":"+username);
        if (hasKey){
            throw new ServiceException("请勿重复登入");
        }
        //向redis做缓存设置过期时间位10分钟，作于校验重复登入，牺牲空间获得效率
        redisTemplate.opsForValue().set("username:"+userInfo.getUserid()+":"+username, null, 10, TimeUnit.MINUTES);
        return userInfo;
    }

    public void logout(String loginName)
    {
        //recordLogService.recordLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }

    /**
     * 注册
     */
    public void register(SysUser sysUser)
    {
        String username = sysUser.getUserName();
        String password = sysUser.getPassword();
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }

        // 注册用户信息
        sysUser.setNickName(username);
        //对注册的密码进行加盐
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        //远程调用system模块进行用户注册
        R<?> registerResult = remoteSysUserService
                .registerUserInfo(sysUser, SecurityConstants.INNER);

        if (R.FAIL == registerResult.getCode())
        {
            throw new ServiceException(registerResult.getMsg());
        }
        //日志记录
        //recordLogService.recordLogininfor(username, Constants.REGISTER, "注册成功");
    }

    public void resetPwd(LoginBody form) {
        String username = form.getUsername();
        String password = form.getPassword();
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }
        R<LoginUser> userInfo = remoteSysUserService.getUserInfo(username, SecurityConstants.INNER);
        SysUser sysUser = userInfo.getData().getSysUser();
        //对修改的的密码进行加盐
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        //远程调用system模块进行用户注册
        AjaxResult registerResult = remoteSysUserService
                .resetPwd(sysUser, SecurityConstants.INNER);

        if (R.FAIL == (Integer) registerResult.get("code"))
        {
            throw new ServiceException((String) registerResult.get("msg"));
        }
        //日志记录
    }
}
