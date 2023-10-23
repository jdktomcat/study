package jdktomcat.log.demo.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Slf4j
@SpringBootApplication
public class LogDemoRedisApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LogDemoRedisApplication.class, args);
    }

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void run(String... args) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent("bbb", "ddd");
        log.info("save key result:{}",result);
        redisTemplate.opsForValue().set("aaa", "bbb", Duration.ofMinutes(1));
        Object ret = redisTemplate.opsForValue().get("aaa");
        log.info("get key result:{}",ret);
        Boolean deleteResult = redisTemplate.delete("bbb");
        log.info("delete key result:{}",deleteResult);
        Object hashRet = redisTemplate.opsForHash().get("abc", "def");
        log.info("get hash key result:{}",hashRet);
    }
}
