package com.jdktomcat.demo.redis.redisson.lock.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("`t_merchant`")
public class Merchant {
    /**
     * 商户ID
     */
    @TableId
    private Integer id;

    /**
     * 商户名称
     */
    private String name;

    /**
     * 总余额
     */
    private BigDecimal balance;
    /**
     * 可用余额
     */
    private BigDecimal available;

    /**
     * 冻结余额，用于下发时，冻结申请金额
     */
    private BigDecimal frozen;

    /**
     * 1：启用，0:停用，-1:软删除
     */
    private Integer status;

    private Integer type;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;
}
