package blossom.project.towelove.community.service.impl;

import blossom.project.towelove.community.entity.UserBehaviors;
import blossom.project.towelove.community.mapper.UserBehaviorsMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class UserBehaviorsService extends ServiceImpl<UserBehaviorsMapper, UserBehaviors> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void recordUserBehavior(UserBehaviors userBehaviors) {
        // 记录到数据库
        this.save(userBehaviors);
        // 记录到 Redis
        String redisKey = "user:behavior:" + userBehaviors.getUserId();
        redisTemplate.opsForList().rightPush(redisKey, userBehaviors);
        redisTemplate.expire(redisKey, 7, TimeUnit.DAYS);
    }

    public List<UserBehaviors> getUserBehaviors(Long userId) {
        return this.list(new QueryWrapper<UserBehaviors>().eq("user_id", userId));
    }
}