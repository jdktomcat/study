package com.jdktomcat.demo.delay.message.mq.consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class DemoDelayMessageMQConsumerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DemoDelayMessageMQConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) {

    }
}
