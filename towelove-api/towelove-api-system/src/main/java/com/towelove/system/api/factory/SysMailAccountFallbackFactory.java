package com.towelove.system.api.factory;

import com.towelove.common.core.domain.R;
import com.towelove.system.api.RemoteSysMailAccountService;
import com.towelove.system.api.domain.SysMailAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2023/3/14 10:05
 * SysMailAccountFallbackFactory类
 */
@Component
public class SysMailAccountFallbackFactory implements FallbackFactory<RemoteSysMailAccountService> {
    private static final Logger log = LoggerFactory.getLogger(SysMailAccountFallbackFactory.class);

    @Override
    public RemoteSysMailAccountService create(Throwable throwable) {
        log.error("获取系统邮箱账号信息调用失败:{}", throwable.getMessage());

        return new RemoteSysMailAccountService() {
            @Override
            public R<Long> getMailAccountByUserId(Long id) {
                return R.fail("根据ID获取邮箱账户失败:"+throwable.getMessage());
            }
        };
    }
}
