package blossom.project.towelove.framework.user.core;

import java.util.Optional;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.framework.log.core
 * @className: UserInfoContextHolder
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/22 14:12
 * @version: 1.0
 * 用户信息上下文容器
 */
public class UserInfoContextHolder {
    private static final ThreadLocal<UserInfoDTO> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(UserInfoDTO userInfoDTO){
        USER_INFO_THREAD_LOCAL.set(userInfoDTO);
    }

    public static Long getUserId(){
        UserInfoDTO userInfoDTO = USER_INFO_THREAD_LOCAL.get();
        return Optional
                .ofNullable(userInfoDTO).map(UserInfoDTO::getId)
                .orElse(null);
    }

    public static String getUserName(){
        UserInfoDTO userInfoDTO = USER_INFO_THREAD_LOCAL.get();
        return Optional
                .ofNullable(userInfoDTO).map(UserInfoDTO::getUserName)
                .orElse(null);
    }

    public static String getNickName(){
        UserInfoDTO userInfo = USER_INFO_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfo).map(UserInfoDTO::getUserName)
                .orElse(null);
    }

    public static String getToken(){
        UserInfoDTO userInfoDTO = USER_INFO_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDTO).map(UserInfoDTO::getToken)
                .orElse(null);
    }

    public static String getSex(){
        UserInfoDTO userInfoDTO = USER_INFO_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDTO).map(UserInfoDTO::getSex)
                .orElse(null);
    }

    public static Long getCoupleId(){
        UserInfoDTO userInfoDTO = USER_INFO_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDTO).map(UserInfoDTO::getCoupleId)
                .orElse(null);
    }
    public static String getPhone(){
        UserInfoDTO userInfoDTO = USER_INFO_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDTO).map(UserInfoDTO::getPhone)
                .orElse(null);
    }public static String getEmail(){
        UserInfoDTO userInfoDTO = USER_INFO_THREAD_LOCAL.get();
        return Optional.ofNullable(userInfoDTO).map(UserInfoDTO::getEmail)
                .orElse(null);
    }

    public static void clean(){
        USER_INFO_THREAD_LOCAL.remove();
    }
}
