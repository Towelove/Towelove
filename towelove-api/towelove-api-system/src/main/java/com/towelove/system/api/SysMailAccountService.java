package com.towelove.system.api;

import com.towelove.common.core.constant.SecurityConstants;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.web.domain.AjaxResult;
import com.towelove.system.api.domain.SysMailAccount;
import com.towelove.system.api.domain.SysUser;
import com.towelove.system.api.factory.SysMailAccountFallbackFactory;
import com.towelove.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 张锦标
 * @date: 2023/3/14 10:04
 * SysMailAccountService接口
 */
@FeignClient(
        //contextId = "sysUserService",
        //value = ServiceNameConstants.SYSTEM_SERVICE,
        value = "towelove-system",
        fallbackFactory = SysMailAccountFallbackFactory.class)
public interface SysMailAccountService {
    @GetMapping("/sys/mail-account/getByUserId")
    R<SysMailAccount> getMailAccountByUserId(@RequestParam("userId") Long userId);
}