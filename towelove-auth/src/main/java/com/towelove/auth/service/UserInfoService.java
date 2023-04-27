package com.towelove.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.towelove.auth.form.LoginVo;
import com.towelove.auth.form.UserAuthVo;
import com.towelove.auth.form.UserInfo;
import com.towelove.auth.form.UserInfoQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: Zhangjinbiao
 * @Date: 2022/11/18 14:46
 * @Connection: qq460219753 wx15377920718
 * Description:
 * 这个UserInfoServiceImpl实现的是User以及UserInfo的所有方法
 * User的方法提供给admin后台管理员进行管理
 * UserInfo的方法则提供给前台用户进行使用
 * Version: 1.0.0
 */
public interface UserInfoService extends IService<UserInfo> {
    Map<String, Object> login(LoginVo loginVo);

    UserInfo selectWxInfoOpenId(String openid);
    //用户认证
    void userAuth(Long userId, UserAuthVo userAuthVo);
    //用户列表（条件查询带分页）
    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);
    /**
     * 用户锁定
     * @param userId
     * @param status 0：锁定 1：正常
     */
    void lock(Long userId, Integer status);
    /**
     * 详情
     * @param userId
     * @return
     */
    Map<String, Object> show(Long userId);
    /**
     * 认证审批
     * @param userId
     * @param authStatus 2：通过 -1：不通过
     */
    void approval(Long userId, Integer authStatus);
}
