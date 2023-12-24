package com.jdktomcat.demo.redis.redisson.command;

import com.jdktomcat.demo.redis.redisson.command.component.RedisComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZF-Timmy
 * @version V1.0
 * @description 类描述：
 * @date 2023/11/13
 */
@SpringBootApplication
public class DemoRedisRedissonCommandApplication implements CommandLineRunner {

    @Autowired
    private RedisComponent redisComponent;

    public static void main(String[] args) {
        SpringApplication.run(DemoRedisRedissonCommandApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {
        String key = "myzset";
        redisComponent.del(key);
        System.out.println("删除键：" + key);
        for (int i = 0; i < 100; i++) {
            boolean add = redisComponent.zadd(key, "field" + i, i);
            System.out.println("元素" + i + " 添加结果：" + add);
        }
        for (int i = 0; i < 100; i++) {
            Object object = redisComponent.zPopMin("myzset");
            System.out.println(object.toString());
        }
    }
}
