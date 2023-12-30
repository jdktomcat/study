package com.jdktomcat.demo.redis.redisson.command.component;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author ZF-Timmy
 * @version V1.0
 * @description 类描述：redis组件
 * @date 2023/11/8
 */
@Slf4j
@Component
public class RedisComponent {

//    @Autowired
//    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取字符串
     *
     * @param key 键值
     * @return 缓存字符串
     */
    public String getPlainString(String key) {
        return (String) redisTemplate.execute((RedisCallback<String>) connection -> new String(connection.get(key.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
    }

//    public Object zPopMin(String key) {
//        return redissonClient.getScoredSortedSet(key).pollFirst();
//    }
//
//    public double firstScore(String key) {
//        return redissonClient.getScoredSortedSet(key).firstScore();
//    }
//
//    public boolean zadd(String key, Object field, double score) {
//        return redissonClient.getScoredSortedSet(key).add(score, field);
//    }
//
//    public boolean del(String key) {
//        return redissonClient.getScoredSortedSet(key).delete();
//    }
}
