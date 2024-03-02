package com.jdktomcat.demo.redis.redisson.lock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdktomcat.demo.redis.redisson.lock.model.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface MerchantMapper extends BaseMapper<Merchant> {

    @Update("update typayv2.t_merchant set available = (available - #{amount}),frozen = (frozen + #{amount}) where id = #{id} and available >= #{amount} and balance >= #{amount}")
    int frozen(@Param("id") Integer id, @Param("amount") BigDecimal amount);
}
