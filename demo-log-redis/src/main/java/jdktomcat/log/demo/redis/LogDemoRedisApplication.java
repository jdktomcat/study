package jdktomcat.log.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("redisTemplate")
    @Autowired
    RedisTemplate redis;

    @Override
    public void run(String... args) {
        redis.opsForValue().setIfAbsent("bbb", "ddd");
        redis.opsForValue().set("aaa", "bbb", Duration.ofMinutes(1));
        Object ret = redis.opsForValue().get("aaa");

        redis.delete("bbb");

        redis.opsForHash().get("abc", "def");
        //System.out.println("redis操作返回: " + ret);
    }
}
