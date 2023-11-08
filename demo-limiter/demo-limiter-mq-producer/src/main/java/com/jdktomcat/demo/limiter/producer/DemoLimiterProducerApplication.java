package com.jdktomcat.demo.limiter.producer;

import com.jdktomcat.demo.limiter.producer.component.MessageProducerComponent;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoLimiterProducerApplication implements CommandLineRunner {

    @Autowired
    private MessageProducerComponent messageProducerComponent;


    public static void main(String[] args) {
        SpringApplication.run(DemoLimiterProducerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Send string
        int i = 0;
        while (i < 10000) {
            SendResult sendResult = messageProducerComponent.send("Hello, RocketMQ World! message" + (i++));
            System.out.printf("syncSend to sendResult=%s %n", sendResult);
        }
    }
}

