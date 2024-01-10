package jdktomcat.demo.skywalking.gateway.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 */

@RestController
@Slf4j
public class HelloController {

    @GetMapping("/say")
    public String say(String name) {
        log.info("hello {}", name);
        return "hello " + name;
    }
}
