package blossom.project.towelove.user.interceptor;


import blossom.project.towelove.user.entity.SysUser;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.interceptor
 * @className: UserInfoContextHolder
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 14:45
 * @version: 1.0
 */
public class UserInfoContextHolder {
    private static final ThreadLocal<SysUser> userCache = new ThreadLocal<>();

    public static void setUserInfo(SysUser sysUser){
        userCache.set(sysUser);
    }

    public static SysUser getUserInfo(){
        return userCache.get();
    }

    public static void clean(){
        userCache.remove();
    }
}
