package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.RemoteUserService;
import blossom.project.towelove.common.constant.SecurityConstants;
import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.mailaccount.MailAccountResponse;
import blossom.project.towelove.common.response.user.SysUserPermissionDto;
import blossom.project.towelove.common.response.user.SysUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:zhang.blossom
 * @Date：2023-11-22 14:10
 */
@Component
//使用下面这个注解必须保证该类的类路径被配置到
//META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
//@AutoConfiguration
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable throwable) {
        return new RemoteUserService() {

            @Override
            public Result<String> saveUser(SysUser sysUser) {
                return Result.fail(null,SecurityConstants.REQUEST_ID);
            }

            @Override
            public Result<SysUserVo> getUserById(Long userId) {
                return Result.fail(null,SecurityConstants.REQUEST_ID);
            }

            @Override
            public Result<String> findUserByPhoneOrEmail(AuthLoginRequest authLoginRequest) {
                return Result.fail(null,SecurityConstants.REQUEST_ID);
            }

            @Override
            public Result<List<SysUserPermissionDto>> getUserPermissionByUserId(Long userId) {
                return Result.fail(null,SecurityConstants.REQUEST_ID);
            }
        };
    }
}
