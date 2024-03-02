package com.jdktomcat.demo.redis.delay.keyspace.notify.service.impl.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdktomcat.demo.redis.delay.keyspace.notify.component.lock.RedisLockHelper;
import com.jdktomcat.demo.redis.delay.keyspace.notify.mapper.BalanceRecordMapper;
import com.jdktomcat.demo.redis.delay.keyspace.notify.mapper.MerchantMapper;
import com.jdktomcat.demo.redis.delay.keyspace.notify.model.BalanceRecord;
import com.jdktomcat.demo.redis.delay.keyspace.notify.model.Merchant;
import com.jdktomcat.demo.redis.delay.keyspace.notify.service.impl.IDeductionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Slf4j
@Service
public class DeductionServiceImpl  implements IDeductionService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private BalanceRecordMapper balanceRecordMapper;

    @Autowired
    private RedisLockHelper redisLockHelper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String withdrawDeduction(Integer merchantId, String orderId, BigDecimal amount) {
        try(RedisLockHelper.AutoCloseRedisLock ignore = redisLockHelper.merchantBalanceLock(merchantId)){
            Merchant preMerchant = merchantMapper.selectById(merchantId);
            log.info("商户:{} 账变前账户余额记录,总余额：{} 可用余额：{} 冻结余额：{}", preMerchant.getName(), preMerchant.getBalance(), preMerchant.getAvailable(), preMerchant.getFrozen());
            int rows = merchantMapper.frozen(merchantId, amount);
            Assert.isTrue(rows > 0, String.format("冻结失败！merchantId: %s", merchantId));
            Merchant postMerchant = merchantMapper.selectById(merchantId);
            log.info("商户:{} 账变后账户余额记录,总余额：{} 可用余额：{} 冻结余额：{}", postMerchant.getName(), postMerchant.getBalance(), postMerchant.getAvailable(), postMerchant.getFrozen());
            BalanceRecord balanceRecord = new BalanceRecord();
            balanceRecord.setOrderId(orderId);
            balanceRecord.setMerchantId(merchantId);
            balanceRecord.setAmount(amount);
            balanceRecord.setPreBalance(preMerchant.getBalance());
            balanceRecord.setPreAvailable(preMerchant.getAvailable());
            balanceRecord.setPreFrozen(preMerchant.getFrozen());
            balanceRecord.setPostBalance(postMerchant.getBalance());
            balanceRecord.setPostAvailable(postMerchant.getAvailable());
            balanceRecord.setPostFrozen(postMerchant.getFrozen());
            int insert = balanceRecordMapper.insert(balanceRecord);
            Assert.isTrue(insert > 0, String.format("保存记录失败！orderId: %s", orderId));
            log.info("保存商户帐变记录:{} 保存结果:{}", JSONObject.toJSONString(balanceRecord), insert);
            return "withdraw success!";
        }catch (Exception ex) {
            log.error("商户取款结算计算账户流水余额过程中出现异常！", ex);
            throw new RuntimeException("商户取款结算计算账户流水余额过程中出现异常！", ex);
        }
    }
}
