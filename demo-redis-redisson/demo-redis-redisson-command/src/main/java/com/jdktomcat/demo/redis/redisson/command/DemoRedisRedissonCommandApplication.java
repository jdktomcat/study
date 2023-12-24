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
        Object object = redisComponent.zPopMin("zadd-1");
        System.out.printf(object.toString());
    }
}
