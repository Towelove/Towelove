package blossom.project.towelove.common.constant;

/**
 * Token的Key常量
 * 
 * @author: 张锦标
 * @date: 2023/2/23 18:36
 * Description:
 */
public class TokenConstant
{
    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "Bearer ";

    /**
     * 令牌秘钥
     */
    public final static String SECRET = "abcdefghijklmnopqrstuvwxyz";

    public final static String USER_ID_HEADER = "X-USER-ID";
    public final static String USER_NAME_HEADER = "X-USER-NAME";
    public final static String USER_NICK_HEADER = "X-NICK-NAME";

    public final static String USER_TOKEN = "Authorization";
    public final static String USER_SEX = "X-USER-SEX";

    public final static String USER_COUPLE_ID = "X-USER-COUPLE";
    public final static Long USER_PERMISSION_CODE = 1L;
    public final static Long ADMIN_PERMISSION_CODE = 2L;

    public static final String USER_PHONE = "X-USER-PHONE";
    public static final String USER_EMAIL = "X-USER-EMAIL";
}
