package com.jdktomcat.demo.limiter.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.jdktomcat.demo.limiter.consumer.component.HttpComponent;
import com.jdktomcat.demo.limiter.consumer.component.RedisComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
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

    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private ScheduledExecutorService consumerExecutor = new ScheduledThreadPoolExecutor(1);

    private static final String CHAT_ID = "";

    private static final String URL = "https://api.telegram.org/bot910445509:AAH4Wbt6__Mrwjyj69vT1Hmi3CQ5yIdkAY0/sendMessage";

    private static final String LOCK_PREFIX = "telegram_send_lock";

    static Map<String,Object> headers = new HashMap<>();

    @Autowired
    private RedisComponent redisComponent;

    private HttpComponent httpComponent;

    static {
        headers.put("Referer", "");
        headers.put("Authorization", "eXwdrXrvrjsHDs7F");
    }

    /**
     * 消费电报消息
     * 电报地址： https://api.telegram.org/bot910445509:AAH4Wbt6__Mrwjyj69vT1Hmi3CQ5yIdkAY0/sendMessage
     */
    @PostConstruct
    public synchronized void consumerTelegramMsg() {
        consumerExecutor.scheduleAtFixedRate(() -> {
            if(queue.isEmpty()){
                return;
            }
            boolean flag = redisComponent.tryLockWithLeaseTime(LOCK_PREFIX, 3);
            if(flag) {
                try {
                    String msg = queue.take();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("chat_id",CHAT_ID);
                    jsonObject.put("parse_mode","HTML");
                    jsonObject.put("text", msg);
                    String ret = httpComponent.postForJson(URL, headers, jsonObject.toJSONString());
                    log.info("telegram send msg success! url:{} data:{} result:{}", URL, jsonObject.toJSONString(),ret);
                } catch (Exception e) {
                    System.out.println("异常："+e.getMessage());
                }
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void shutdown(){
        consumerExecutor.shutdown();
    }


    @Override
    public void onMessage(String s) {
        System.out.println("消费消息：" + s);
        try {
            queue.put(s);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
