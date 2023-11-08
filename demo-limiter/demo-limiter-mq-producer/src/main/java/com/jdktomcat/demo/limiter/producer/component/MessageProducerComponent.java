package com.jdktomcat.demo.limiter.producer.component;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ZF-Timmy
 * @version V1.0
 * @description 类描述：消息生产者服务组件
 * @date 2023/11/8
 */
@Component
public class MessageProducerComponent {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${demo.rocketmq.topic}")
    private String topic;

    /**
     * 发送消息
     *
     * @param message 消息
     * @return 发送结果
     */
    public SendResult send(String message) {
        return rocketMQTemplate.syncSend(topic, message);
    }

}
