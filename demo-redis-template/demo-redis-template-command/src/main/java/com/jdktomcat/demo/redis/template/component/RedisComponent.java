package com.jdktomcat.demo.redis.template.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

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
    private RedisTemplate redisTemplate;

    public boolean del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                return redisTemplate.delete(key[0]);
            } else {
                return redisTemplate.delete(CollectionUtils.arrayToList(key)) == key.length;
            }
        }
        return false;
    }

    public boolean set(String key, String value) {
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize(key), serializer.serialize(value));
            return true;
        });
        return result;
    }

    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("redis Exception: {}", e.getMessage(), e);
            return false;
        }
    }


    public long ttl(String key) {
        try {
            return (long) redisTemplate.execute((RedisCallback) connection -> connection.ttl(key.getBytes()));
        } catch (Exception ex) {
            log.error("redis Exception: {}", ex.getMessage(), ex);
            return 0;
        }
    }

    public long getExpire(String key) {
        try {
            return redisTemplate.opsForValue().getOperations().getExpire(key);
        } catch (Exception e) {
            log.error("redis Exception: {}", e.getMessage(), e);
            return 0;
        }
    }

    public Object get(String key) {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            return null;
        }
    }
}
