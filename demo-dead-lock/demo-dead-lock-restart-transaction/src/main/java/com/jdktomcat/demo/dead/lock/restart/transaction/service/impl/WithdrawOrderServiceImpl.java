package com.jdktomcat.demo.dead.lock.restart.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jdktomcat.demo.dead.lock.restart.transaction.mapper.WithdrawOrderMapper;
import com.jdktomcat.demo.dead.lock.restart.transaction.model.WithdrawOrder;
import com.jdktomcat.demo.dead.lock.restart.transaction.service.IWithdrawOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WithdrawOrderServiceImpl implements IWithdrawOrderService {

    @Autowired
    private WithdrawOrderMapper withdrawOrderMapper;

    @Override
    @Transactional
    public String actionOne() {
        WithdrawOrder updateEntry1 = new WithdrawOrder();
        updateEntry1.setOrderStatus(1);
        int update1 = withdrawOrderMapper.update(updateEntry1, new QueryWrapper<WithdrawOrder>().eq("OrderId","MW120240131205432608682300"));
        log.info("action one update1:{}",update1);
        try{
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception ex){
            log.error("occur some exception!",ex);
        }
        WithdrawOrder updateEntry2 = new WithdrawOrder();
        updateEntry2.setParentOrderStatus(1);
        int update2 = withdrawOrderMapper.update(updateEntry2, new QueryWrapper<WithdrawOrder>().in("OrderId","MW120240131205432601682300","MW120240131205432593682300"));
        log.info("action one update2:{}", update2);
        return "action one have done!";
    }

    @Override
    @Transactional
    public String actionTwo() {
        WithdrawOrder updateEntry = new WithdrawOrder();
        updateEntry.setParentOrderStatus(1);
        Object[] orderIds = {
                "MW120240131205423765682300",
                "MW120240131205423773682300",
                "MW120240131205432593682300",
                "MW120240131205432601682300",
                "MW120240131205432608682300",
                "H3000054240131205437745628f00",
                "H3000074240131205444642629100",
                "H3000054240131205457361629100",
                "MW120240131205457394681100",
                "MW120240131205457402681100",
                "MW120240131205457410681100",
                "MW120240131205457420681400",
                "MW120240131205457413681400",
                "MW120240131205457428681401",
                "MW120240131205457420681401",
                "MW120240131205457428681400",
                "H3000054240131205457814629000",
                "H3002054240131205458285629100",
                "H3007404240131205459194629000",
                "H3001704240131205501708629100"
        };
        int update = withdrawOrderMapper.update(updateEntry, new QueryWrapper<WithdrawOrder>().in("OrderId",orderIds));
        log.info("action two update:{}", update);
        return "action two have done!";
    }
}
