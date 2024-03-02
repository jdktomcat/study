package com.jdktomcat.demo.redis.delay.keyspace.notify.service.impl;

import java.math.BigDecimal;

public interface IWithdrawOrderService {

    String actionOne();

    String actionTwo();

    String actionThree();

    String merchantWithdraw(Integer merchantId, String orderId, BigDecimal amount);
}
