package com.towelove.system.event;

import com.towelove.system.api.domain.SysUser;
import org.springframework.context.ApplicationEvent;

/**
 * @author: 张锦标Blossom
 * @date: 2023-3-8
 * 祝天下所有妇女节日快乐
 * 当前类继承ApplicationEvent类
 * 完成用户注册事件的监听
 */
public class SysUserRegisterEvent extends ApplicationEvent {

    /**
     * 注册的用户用户信息
     */
    private SysUser sysUser;


    public SysUserRegisterEvent(Object source) {
        super(source);
    }

    public SysUserRegisterEvent(Object source, SysUser sysUser) {
        super(source);
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

}