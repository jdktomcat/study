package com.jdktomcat.demo.redis.delay.keyspace.notify.web.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest {

    private Integer merchantId;

    private String orderId;

    private BigDecimal amount;

}
