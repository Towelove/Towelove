package blossom.project.towelove.auth.strategy;


import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.user.strategy
 * @className: UserRegisterStrategyFactory
 * @author: Link Ji
 * @description:
 * @date: 2023/11/24 21:15
 * @version: 1.0
 */
public class UserRegisterStrategyFactory {



    private static Map<String, UserAccessStrategy> userRegisterStrategyMap = new HashMap<>();

    public static UserAccessStrategy userRegisterStrategy(String type){
        return userRegisterStrategyMap.get(type);
    }

    public static void register(String key, UserAccessStrategy userAccessStrategy){
        userRegisterStrategyMap.put(key, userAccessStrategy);
    }
}
