package com.jdktomcat.demo.redis.delay.keyspace.notify.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdktomcat.demo.redis.delay.keyspace.notify.model.BalanceRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BalanceRecordMapper extends BaseMapper<BalanceRecord> {
}
