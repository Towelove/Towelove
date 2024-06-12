package blossom.project.towelove.community.service.impl;

import blossom.project.towelove.community.entity.UserBehaviors;
import blossom.project.towelove.community.service.impl.UserBehaviorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInterestService {
    @Autowired
    private UserBehaviorsService userBehaviorsService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final Map<String, Integer> BEHAVIOR_WEIGHTS = Map.of(
            "view", 1,
            "like", 2,
            "comment", 3,
            "favorite", 4
    );

    public Map<String, Double> calculateUserInterest(Long userId) {
        // 从 Redis 中获取用户行为
        String redisKey = "user:behavior:" + userId;
        List<Object> behaviors = redisTemplate.opsForList().range(redisKey, 0, -1);

        if (behaviors == null || behaviors.isEmpty()) {
            // 如果 Redis 中没有，查数据库
            behaviors = userBehaviorsService.getUserBehaviors(userId).stream()
                    .map(obj -> (Object) obj)
                    .collect(Collectors.toList());
        }

        // 计算兴趣权重
        Map<String, Double> interestMap = new HashMap<>();
        for (Object behaviorObj : behaviors) {
            UserBehaviors behavior = (UserBehaviors) behaviorObj;
            for (String tag : behavior.getTagList()) {
                interestMap.putIfAbsent(tag, 0.0);
                interestMap.put(tag, interestMap.get(tag) + BEHAVIOR_WEIGHTS.get(behavior.getBehaviorType()));
            }
        }

        return interestMap;
    }
}
