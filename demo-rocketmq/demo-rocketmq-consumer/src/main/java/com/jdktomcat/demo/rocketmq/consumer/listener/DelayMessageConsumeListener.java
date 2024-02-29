package com.jdktomcat.demo.rocketmq.consumer.listener;

import com.jdktomcat.demo.rocketmq.consumer.component.RedisComponent;
import com.jdktomcat.demo.rocketmq.consumer.constant.RocketMQConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DelayMessageConsumeListener extends BaseConsumerListener {

    /**
     * redis工具类客户端
     */
    @Autowired
    private RedisComponent redisComponent;

    @Override
    protected void consume(String tag, String topic, MessageExt messageExt) {
        try {
            log.info("----------延迟消息处理start----------");
            log.info("延迟消息消费原始內容:{}", toString(messageExt));
            String body = new String(messageExt.getBody(), StandardCharsets.UTF_8).trim();
            if (tag.equals(RocketMQConstant.DELAY_MESSAGE_TOPIC_TAG_VOUCHER)) {
                if (StringUtils.isNotEmpty(body)) {
                    Object value = redisComponent.get(body);
                    String valueString = (value == null ? "" : value.toString());
                    if (StringUtils.isEmpty(valueString)) {
                        redisComponent.setIfAbsent(body,body,3, TimeUnit.SECONDS);
                    }
                    log.warn("商户订单{}银行回单编码验证确认超时", body);
                }
            } else {
                log.warn("该延迟消息tag:{}没有对应的处理逻辑！", tag);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
