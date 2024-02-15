package com.jdktomcat.demo.redis.redisson.lock.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("`t_balance_record`")
public class BalanceRecord {

    @TableId(value = "order_id")
    private String orderId;

    @TableField(value = "merchant_id")
    private Integer merchantId;

    private BigDecimal amount;

    @TableField(value = "pre_balance")
    private BigDecimal preBalance;

    @TableField(value = "pre_available")
    private BigDecimal preAvailable;

    @TableField(value = "pre_frozen")
    private BigDecimal preFrozen;

    @TableField(value = "post_balance")
    private BigDecimal postBalance;

    @TableField(value = "post_available")
    private BigDecimal postAvailable;

    @TableField(value = "post_frozen")
    private BigDecimal postFrozen;

    @TableField(value = "create_time")
    private Date createTime;
}
