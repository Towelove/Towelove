package blossom.project.towelove.auth.strategy;


import blossom.project.towelove.common.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public static UserAccessStrategy userAccessStrategy(String type){
        UserAccessStrategy userAccessStrategy = userRegisterStrategyMap.get(type);
        if (Objects.isNull(userAccessStrategy)){
            throw new ServiceException("非法请求，注册渠道错误");
        }
        return userAccessStrategy;
    }

    public static void register(String key, UserAccessStrategy userAccessStrategy){
        userRegisterStrategyMap.put(key, userAccessStrategy);
    }
}
