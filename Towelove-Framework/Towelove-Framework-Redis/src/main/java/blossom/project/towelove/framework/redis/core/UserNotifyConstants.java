package blossom.project.towelove.framework.redis.core;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.redisMQ
 * @className: UserNotifyConstans
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 18:14
 * @version: 1.0
 */
public class UserNotifyConstants {
    public static final String NOTIFY_STREAM = "user-notify-stream";
    public static final String USER_CONSUMER_GROUP = "user-consumer";

    public static final String USER_NOTIFY_QUEUE = "user_notify";

    public static final String USER_NOTIFY_TO_DB_QUEUE = "user_notify_to_db";

    public static final String USER_NOTIFY_REFERRED_RESULT = "user_notify_referred_result";
}
