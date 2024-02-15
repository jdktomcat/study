package com.jdktomcat.demo.redis.redisson.lock.web.controller;

import com.jdktomcat.demo.redis.redisson.lock.service.IWithdrawOrderService;
import com.jdktomcat.demo.redis.redisson.lock.web.vo.Result;
import com.jdktomcat.demo.redis.redisson.lock.web.vo.WithdrawRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IWithdrawOrderService withdrawOrderService;

    @GetMapping("/one")
    public Result<?> one(){
        //统一返回值
        try {
            return Result.success(withdrawOrderService.actionOne());
        }catch (Exception exception){
            return Result.failed(exception.getMessage());
        }
    }

    @GetMapping("/two")
    public Result<?> two(){
        //统一返回值
        return Result.success(withdrawOrderService.actionTwo());
    }

    @GetMapping("/three")
    public Result<?> three(){
        //统一返回值
        return Result.success(withdrawOrderService.actionThree());
    }

    @PostMapping("/withdraw")
    public Result<?> withdraw(@RequestBody WithdrawRequest withdrawRequest){
        //统一返回值
        return Result.success(withdrawOrderService.merchantWithdraw(withdrawRequest.getMerchantId(),withdrawRequest.getOrderId(),withdrawRequest.getAmount()));
    }
}
