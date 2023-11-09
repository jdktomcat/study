package com.jdktomcat.demo.limiter.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.jdktomcat.demo.limiter.consumer.component.HttpComponent;
import com.jdktomcat.demo.limiter.consumer.component.RedisComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author ZF-Timmy
 * @version V1.0
 * @description 类描述：消费者
 * @date 2023/11/8
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.topic}", consumerGroup = "timmy_consumer", tlsEnable = "${demo.rocketmq.tlsEnable}")
public class StringConsumerListener implements RocketMQListener<String> {

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private final ScheduledExecutorService consumerExecutor = new ScheduledThreadPoolExecutor(1);

    private static final String CHAT_ID = "6697826662";

    private static final String URL = "https://api.telegram.org/bot6347956777:AAFzJ2YtxunXH_ohDJdPvkGDoXk8ncoCO_4/sendMessage";

    private static final String LOCK_PREFIX = "telegram_send_lock";

    @Autowired
    private RedisComponent redisComponent;

    @Autowired
    private HttpComponent httpComponent;


    /**
     * 消费电报消息
     * 电报地址： https://api.telegram.org/bot910445509:AAH4Wbt6__Mrwjyj69vT1Hmi3CQ5yIdkAY0/sendMessage
     */
    @PostConstruct
    public synchronized void consumerTelegramMsg() {
        consumerExecutor.scheduleAtFixedRate(() -> {
            if (queue.isEmpty()) {
                return;
            }
            boolean flag = redisComponent.tryLockWithLeaseTime(LOCK_PREFIX, 3);
            if (flag) {
                try {
                    String msg = queue.take();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("chat_id", CHAT_ID);
                    jsonObject.put("parse_mode", "HTML");
                    jsonObject.put("text", msg);
                    String ret = httpComponent.postForJson(URL, new HashMap<>(), jsonObject.toJSONString());
                    log.info("telegram send msg success! url:{} data:{} result:{}", URL, jsonObject.toJSONString(), ret);
                } catch (Exception e) {
                    log.error("发送告警信息发生异常！", e);
                }
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void shutdown() {
        consumerExecutor.shutdown();
    }

    @Override
    public void onMessage(String message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            log.error("消费消息发生异常！", e);
        }
    }
}
