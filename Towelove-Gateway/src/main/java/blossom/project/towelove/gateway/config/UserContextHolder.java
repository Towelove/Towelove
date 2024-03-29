package blossom.project.towelove.gateway.config;

import blossom.project.towelove.common.response.user.LoginUserResponse;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.gateway.config
 * @className: UserContextHolder
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/29 16:46
 * @version: 1.0
 */
public class UserContextHolder {
    private static final ThreadLocal<LoginUserResponse> threadLocal = new ThreadLocal<>();

    public static void setUserInfo(LoginUserResponse userInfo){
        threadLocal.set(userInfo);
    }

    public static LoginUserResponse getUserInfo(){
        return threadLocal.get();
    }

    public static void clean() {
        threadLocal.remove();
    }
}
