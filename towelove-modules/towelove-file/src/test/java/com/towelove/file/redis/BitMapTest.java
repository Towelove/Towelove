package com.towelove.file.redis;

import com.towelove.common.core.constant.RedisServiceConstants;
import com.towelove.common.redis.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.script.RedisScript;
import sun.awt.util.IdentityArrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/30 13:10
 * BitMapTest类
 */
@SpringBootTest
public class BitMapTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void bitmap() {
        Long userId = 1L;
        Long articleId = 124L;
        String key = RedisServiceConstants.USER_LIKE_ARTICLE + userId;
        System.out.println(redisService.setBit(key,
                articleId, Boolean.TRUE));
        System.out.println(redisService.getBit(key, articleId));
        String keyTime = RedisServiceConstants.USER_LIKE_TIME + userId;
        List<Long> list = new ArrayList<>(500);
        list.add(articleId);
        System.out.println(redisService.setCacheList(keyTime, list));
        System.out.println(redisService.getCacheList(keyTime));
        System.out.println("---------------------");
        long length = redisService.bitmapSize(key);
        System.out.println(length);
        System.out.println(redisService.bitCount(key));
        long i = redisService.bitCountRange(key, 0L, 128L);
        System.out.println(i);
        List<Long> articleIds = new ArrayList<>();
        for (long j = 0; j < length; j += 128) {
            if (redisService.bitCountRange(key, j, j + 128) == 0) {
                continue;
            } else {
                for (long h = j; h < j + 128; h++) {
                    if (redisService.getBit(key,h))
                    articleIds.add(h);
                }
            }
        }
        System.out.println(articleIds);

    }
}
