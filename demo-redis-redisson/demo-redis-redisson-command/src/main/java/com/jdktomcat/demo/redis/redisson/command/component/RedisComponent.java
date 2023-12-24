package com.jdktomcat.demo.redis.redisson.command.component;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ZF-Timmy
 * @version V1.0
 * @description 类描述：redis组件
 * @date 2023/11/8
 */
@Slf4j
@Component
public class RedisComponent {

    @Autowired
    private RedissonClient redissonClient;

    public Object zPopMin(String key) {
        return redissonClient.getScoredSortedSet(key).pollFirst();
    }

    public boolean zadd(String key, Object field, double score) {
        return redissonClient.getScoredSortedSet(key).add(score, field);
    }

    public boolean del(String key) {
        return redissonClient.getScoredSortedSet(key).delete();
    }
}
