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

    public final static String USER_ID_HEADER = "X-User-Id";
    public final static Long USER_PERMISSION_CODE = 1L;
    public final static Long ADMIN_PERMISSION_CODE = 2L;

}
