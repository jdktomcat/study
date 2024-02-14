package com.jdktomcat.demo.redis.redisson.lock.service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface IDeductionService {

    @Transactional(rollbackFor = Exception.class)
    String withdrawDeduction(Integer merchantId, String orderId, BigDecimal amount);

}
