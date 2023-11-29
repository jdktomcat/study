package com.jdktomcat.demo.limiter.consumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.jdktomcat.demo.limiter.consumer.component.HttpComponent;
import com.jdktomcat.demo.limiter.consumer.component.RedisComponent;
import com.jdktomcat.demo.limiter.consumer.message.AlertMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    private final ScheduledExecutorService consumerExecutor = new ScheduledThreadPoolExecutor(1);


    private static final String URL = "https://api.telegram.org/bot";

    private static final String API = "/sendMessage";


    @Autowired
    private RedisComponent redisComponent;

    @Autowired
    private HttpComponent httpComponent;

    private static final String BOT_SET_KEY = "telegram:bot:set";

    private static final String BOT_CHAT_SET = "telegram:bot:%s:chat:set";

    private static final String BOT_CHAT_MESSAGE_LIST = "telegram:bot:%s:chat:%s:list";

    private static final String BOT_CHAT_EIGENVALUE = "telegram:bot:%s:chat:%s:limit";

    private static final String BOT_LOCK = "telegram:send:lock:%s";

    private static final String BOT_CHAT_LOCK = "telegram:consume:lock:%s:%s";

    /**
     * 消费电报消息
     */
    @PostConstruct
    public synchronized void consumerTelegramMsg() {
        consumerExecutor.scheduleAtFixedRate(() -> {
            Set<String> botSet = redisComponent.getSetMember(BOT_SET_KEY);
            for (String bot : botSet) {
                if (!redisComponent.tryLockWithLeaseTime(String.format(BOT_LOCK, bot), 30)) {
                    continue;
                }
                Set<String> botChatSet = redisComponent.getSetMember(String.format(BOT_CHAT_SET, bot));
                for (String chat : botChatSet) {
                    if (redisComponent.listLength(String.format(BOT_CHAT_MESSAGE_LIST, bot, chat)) == 0L) {
                        continue;
                    }
                    if (!redisComponent.limitFlowFixedWindow(String.format(BOT_CHAT_EIGENVALUE, bot, chat), 20, 60)) {
                        continue;
                    }
                    String message = redisComponent.pop(String.format(BOT_CHAT_MESSAGE_LIST, bot, chat));
                    if (StringUtils.isEmpty(message)) {
                        continue;
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("chat_id", chat);
                    jsonObject.put("parse_mode", "HTML");
                    jsonObject.put("text", message);
                    String result = httpComponent.postForJson(URL + bot + API, new HashMap<>(), jsonObject.toJSONString());
                    log.info("telegram send msg success! url:{} data:{} result:{}", URL + bot + API, jsonObject.toJSONString(), result);
                }
            }
        }, 0, 30, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    public void shutdown() {
        consumerExecutor.shutdown();
    }

    @Override
    public void onMessage(String message) {
        AlertMessage alertMessage = JSONObject.parseObject(message, AlertMessage.class);
        if (alertMessage != null && !StringUtils.isEmpty(alertMessage.getBot()) && !StringUtils.isEmpty(alertMessage.getChat())) {
            String lockKey = String.format(BOT_CHAT_LOCK, alertMessage.getBot(), alertMessage.getChat());
            while(!redisComponent.getLock(lockKey)){
                try{
                    TimeUnit.MILLISECONDS.sleep(300L);
                }catch (Exception exception){
                    log.warn("线程休眠异常！", exception);
                }
            }
            try {
                Long addBotResult = redisComponent.addSetMember(BOT_SET_KEY, alertMessage.getBot());
                Long addBotChatResult = redisComponent.addSetMember(String.format(BOT_CHAT_SET, alertMessage.getBot()), alertMessage.getChat());
                Long pushResult = redisComponent.add(String.format(BOT_CHAT_MESSAGE_LIST, alertMessage.getBot(), alertMessage.getChat()), alertMessage.getMessage());
                log.info("addBotResult:{} addBotChatResult:{} addBotChatMessage:{}", addBotResult, addBotChatResult, pushResult);
            } finally {
                redisComponent.release(lockKey);
            }
        }
    }
}
