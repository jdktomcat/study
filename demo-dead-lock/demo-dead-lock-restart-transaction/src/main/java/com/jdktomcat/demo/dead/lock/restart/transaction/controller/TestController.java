package com.jdktomcat.demo.dead.lock.restart.transaction.controller;

import com.jdktomcat.demo.dead.lock.restart.transaction.annotation.Limiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZF-Timmy
 * @version V1.0
 * @description 类描述：
 * @date 2023/11/13
 */
@RestController
public class TestController {

    @Limiter()
    @GetMapping("/limiter")
    public String say(String name) {
        return "hello " + name;
    }
}
