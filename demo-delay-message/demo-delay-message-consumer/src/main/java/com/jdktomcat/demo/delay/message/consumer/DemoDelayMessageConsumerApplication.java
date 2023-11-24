package com.jdktomcat.demo.delay.message.consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class DemoDelayMessageConsumerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DemoDelayMessageConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) {

    }
}
