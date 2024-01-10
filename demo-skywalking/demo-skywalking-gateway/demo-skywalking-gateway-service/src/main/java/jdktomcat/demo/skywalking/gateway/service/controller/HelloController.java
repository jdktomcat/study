package jdktomcat.demo.skywalking.gateway.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 */

@RestController
public class HelloController {



    @GetMapping("/say")
    public String say(String name){
        return "";
    }

}
