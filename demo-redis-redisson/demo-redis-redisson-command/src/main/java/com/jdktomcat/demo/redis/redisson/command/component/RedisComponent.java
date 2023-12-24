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

    /**
     * 出栈zset数据结构最小值
     *
     * @param key 有序集合键值
     * @return 最小值
     */
    public Object zPopMin(String key) {
        return redissonClient.getScoredSortedSet(key).pollFirst();
    }
}
