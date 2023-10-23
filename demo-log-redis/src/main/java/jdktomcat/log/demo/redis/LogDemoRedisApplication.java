package jdktomcat.log.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@SpringBootApplication
public class LogDemoRedisApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LogDemoRedisApplication.class, args);
    }

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void run(String... args) {
        redisTemplate.opsForValue().setIfAbsent("bbb", "ddd");
        redisTemplate.opsForValue().set("aaa", "bbb", Duration.ofMinutes(1));
        Object ret = redisTemplate.opsForValue().get("aaa");

        redisTemplate.delete("bbb");

        redisTemplate.opsForHash().get("abc", "def");
        //System.out.println("redis操作返回: " + ret);
    }
}
