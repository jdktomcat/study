package com.jdktomcat.demo.redis.delay.keyspace.notify.web.controller;

import com.jdktomcat.demo.redis.delay.keyspace.notify.web.vo.Result;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程控制器
 */
@RestController
@RequestMapping("/process")
public class ProcessController {

    @GetMapping("/token")
    public Result<?> token(){
        //统一返回值
        try {
            return Result.success(RandomStringUtils.randomAlphanumeric(20));
        }catch (Exception exception){
            return Result.failed(exception.getMessage());
        }
    }

    @GetMapping("/one")
    public Result<?> one(String token){
        //统一返回值
        try {
            return Result.success(token+":"+RandomStringUtils.randomAlphanumeric(20));
        }catch (Exception exception){
            return Result.failed(exception.getMessage());
        }
    }

    @GetMapping("/two")
    public Result<?> two(String token){
        //统一返回值
        try {
            return Result.success(token+":"+RandomStringUtils.randomAlphanumeric(20));
        }catch (Exception exception){
            return Result.failed(exception.getMessage());
        }
    }
}
