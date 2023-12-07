package blossom.project.towelove.auth.strategy;

import blossom.project.towelove.common.domain.dto.SysUser;
import blossom.project.towelove.common.request.auth.AuthLoginRequest;
import blossom.project.towelove.common.request.auth.AuthRegisterRequest;
import org.springframework.beans.factory.InitializingBean;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.strategy
 * @className: UserAccessStrategy
 * @author: Link Ji
 * @description:
 * @date: 2023/11/24 21:16
 * @version: 1.0
 */
public interface UserAccessStrategy extends InitializingBean {
    /**
     * 注册策略
     * @param authRegisterRequest 注册请求参数
     * @return String 返回值为token，失败则返回null
     */

    SysUser register(AuthRegisterRequest authRegisterRequest);

    /**
     * 登入策略
     * @param authLoginRequest 登入请求参数
     * @return String 返回值为token，失败则返回null
     */
    SysUser login(AuthLoginRequest authLoginRequest);
}
