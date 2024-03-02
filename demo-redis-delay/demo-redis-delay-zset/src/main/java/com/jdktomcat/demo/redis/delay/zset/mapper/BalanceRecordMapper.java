package com.jdktomcat.demo.redis.redisson.lock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdktomcat.demo.redis.redisson.lock.model.BalanceRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BalanceRecordMapper extends BaseMapper<BalanceRecord> {
}
