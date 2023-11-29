package com.jdktomcat.demo.limiter.producer;

import com.alibaba.fastjson.JSONObject;
import com.jdktomcat.demo.limiter.producer.component.MessageProducerComponent;
import com.jdktomcat.demo.limiter.producer.message.AlertMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

@Slf4j
@SpringBootApplication
public class DemoLimiterProducerApplication implements CommandLineRunner {

    @Autowired
    private MessageProducerComponent messageProducerComponent;


    public static void main(String[] args) {
        SpringApplication.run(DemoLimiterProducerApplication.class, args);
    }

    /**
     * 模拟机器人
     */
    private static final String[] BOTS = {"6774799455:AAFeHKrLxxMRVpBLg8LFj7N62drOxz7b25g", "6347956777:AAGkrZ2ii7AuiKNiO1LflAGy8owYjTg3oWk", "6860056824:AAHoBVhelMHs6KXRHey2ARiKegaYlfiKpwk"};

    /**
     * 模拟群组
     */
    private static final String[] CHATS = {"-4043624377", "-4086688530", "-4095197160"};


    @Override
    public void run(String... args) {
        // Send string
        int i = 0;
        Random random = new Random();
        while (i < 1000) {
            AlertMessage alertMessage = new AlertMessage();
            String bot = BOTS[random.nextInt(3)];
            alertMessage.setBot(bot);
            String chat = CHATS[random.nextInt(3)];
            alertMessage.setChat(chat);
            alertMessage.setMessage("alert message " + (i++));
            SendResult sendResult = messageProducerComponent.send(JSONObject.toJSONString(alertMessage), bot+chat);
            log.info("syncSend to sendResult:{}", sendResult);
        }
    }
}

