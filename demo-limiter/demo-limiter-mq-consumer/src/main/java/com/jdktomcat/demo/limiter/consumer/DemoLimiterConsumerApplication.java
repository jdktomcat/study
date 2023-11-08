package com.jdktomcat.demo.limiter.consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
public class DemoLimiterConsumerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DemoLimiterConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) {

    }
}
