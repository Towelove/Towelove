package com.towelove.system.event;

import com.towelove.system.api.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * @author: 张锦标Blossom
 * @date: 2023-3-8
 * 祝天下所有妇女节日快乐
 * 当前类继承ApplicationEvent类
 * 完成用户注册事件的监听
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRegisterEvent extends RemoteApplicationEvent {

    /**
     * 注册的用户用户信息
     */
    private SysUser sysUser;

    public SysUserRegisterEvent(){//序列化

    }

    public SysUserRegisterEvent(Object source,String originService,String destinationService, SysUser sysUser) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(
                destinationService));
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

}