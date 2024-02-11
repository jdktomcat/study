package com.jdktomcat.demo.dead.lock.restart.transaction.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("`t_withdraw_order`")
public class WithdrawOrder {

    private Long orderIndex;

    private String orderId;

    private String merchantOrderId;

    private int orderStatus;

    private int priorityMatchStatus;

    private int parentOrderStatus;

    private Date createTime;

}
