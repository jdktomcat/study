package com.jdktomcat.demo.redis.delay.keyspace.notify.service.impl;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface IDeductionService {

    @Transactional(rollbackFor = Exception.class)
    String withdrawDeduction(Integer merchantId, String orderId, BigDecimal amount);

}
