package com.jdktomcat.demo.snowflake.generate.service.impl;

import com.jdktomcat.demo.snowflake.generate.mapper.WithdrawOrderMapper;
import com.jdktomcat.demo.snowflake.generate.model.WithdrawOrder;
import com.jdktomcat.demo.snowflake.generate.service.IWithdrawOrderService;
import com.jdktomcat.demo.snowflake.generate.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class WithdrawOrderServiceImpl implements IWithdrawOrderService {

    @Autowired
    private WithdrawOrderMapper withdrawOrderMapper;

    @Override
    @Transactional
    public String insert() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDHHmmss");
        for(int i=0;i<100;i++){
            WithdrawOrder withdrawOrder = new WithdrawOrder();
            withdrawOrder.setOrderIndex(SnowflakeIdWorker.createTableId());
            withdrawOrder.setOrderId("JSTWZORDER"+ simpleDateFormat.format(new Date())+ RandomStringUtils.random(5).toUpperCase());
            withdrawOrder.setOrderStatus(1);
            withdrawOrder.setParentOrderStatus(1);
            withdrawOrder.setMerchantOrderId("JSTWZMO"+ simpleDateFormat.format(new Date())+ RandomStringUtils.random(5).toUpperCase());
            withdrawOrder.setParentOrderStatus(1);
            withdrawOrder.setCreateTime(new Date());
            int save = withdrawOrderMapper.insert(withdrawOrder);
            log.info("insert order:{} result:{}", withdrawOrder, save);
        }
        return "OK";
    }
}
