package jdktomcat.demo.log.limiter.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZF-Timmy
 * @version V1.0
 * @description 类描述：
 * @date 2023/10/1
 */
@RestController
public class LimiterController {


    private final RedisTemplate<String, String> redisTemplate;

    public LimiterController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("save")
    public String save(String key, String value){
        redisTemplate.opsForValue().set(key, value);
        return "OK";
    }
}
