package com.jdktomcat.demo.redis.redisson.lock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdktomcat.demo.redis.redisson.lock.model.WithdrawOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WithdrawOrderMapper extends BaseMapper<WithdrawOrder> {
    @Select("select OrderId from typayv2.t_withdraw_order where find_in_set(OrderId, #{orderIds}) for update ")
    List<String> queryListForUpdate(String orderIds);
}

