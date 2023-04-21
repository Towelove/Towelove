package com.towelove.system.api;

import com.towelove.common.core.domain.R;
import com.towelove.system.api.factory.SysMailAccountFallbackFactory;
import com.towelove.system.api.model.MailAccountDO;
import com.towelove.system.api.model.MailAccountRespVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/14 10:04
 * SysMailAccountService接口
 */
@FeignClient(
        contextId = "sysMailAccountService",
        //value = ServiceNameConstants.SYSTEM_SERVICE,
        value = "towelove-system",
        fallbackFactory = SysMailAccountFallbackFactory.class)
public interface RemoteSysMailAccountService {
    @GetMapping("/sys/mail-account/getByUserId")
    R<List<MailAccountDO>> getMailAccountByUserId(@RequestParam("userId") Long userId);

    @GetMapping("/sys/mail-account/get")
    public R<MailAccountRespVO> getMailAccount(@RequestParam("id")Long id);
}
