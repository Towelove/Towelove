package blossom.project.towelove.common.constant;

/**
 * @author: 张锦标
 * @date: 2023/3/22 18:42
 * RedisServiceConstants类
 */
public class RedisKeyConstant {
    //系统模块 邮件账户key前缀
    public static final String SYS_MAIL_ACCOUNT = "sys_mail_account:";
    //当前系统黑名单ip
    public static final String BLACK_LIST_IP = "black_list_ip:";
    //当前正在使用系统的ip
    public static final String USING_SYS_IP = "using_sys_ip:";

    public static final String USER_LIKE_ARTICLE = "users:like:article:";

    public static final String USER_LIKE_TIME = "users:like:time:";

    public static final String VALIDATE_CODE = "validate:code:";

    public static final String VALIDATE_CODE_SUBJECT = "Towelove<验证码>";

    public static final String REMIND_SUBJECT = "Towelove<提醒>";
}
