package com.jdktomcat.demo.redis.redisson.lock.service;

import com.jdktomcat.demo.redis.redisson.lock.model.Merchant;

import java.math.BigDecimal;

public interface IWithdrawOrderService {

    String actionOne();

    String actionTwo();

    String actionThree();

    String merchantWithdraw(Integer merchantId, String orderId, BigDecimal amount);
}
