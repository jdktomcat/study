package com.jdktomcat.demo.dead.lock.restart.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdktomcat.demo.dead.lock.restart.transaction.model.WithdrawOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WithdrawOrderMapper extends BaseMapper<WithdrawOrder> {
    @Select("select * from typayv2.t_withdraw_order where find_in_set(#{orderIds}, OrderId)")
    List<WithdrawOrder> queryListForUpdate(String orderIds);

}
