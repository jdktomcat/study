package com.jdktomcat.demo.redis.redisson.lock.web.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest {

    private Integer merchantId;

    private String orderId;

    private BigDecimal amount;

}
