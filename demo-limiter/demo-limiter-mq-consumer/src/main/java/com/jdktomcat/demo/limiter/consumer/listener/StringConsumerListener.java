package com.jdktomcat.demo.limiter.consumer.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author ZF-Timmy
 * @version V1.0
 * @description 类描述：消费者
 * @date 2023/11/8
 */

@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.topic}", consumerGroup = "timmy_consumer", tlsEnable = "${demo.rocketmq.tlsEnable}")
public class StringConsumerListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("消费消息：" + s);
    }
}
