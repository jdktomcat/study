package jdktomcat.demo.log.rocketmq;

import jdktomcat.demo.log.rocketmq.rabbit.Producer;
import jdktomcat.demo.log.rocketmq.util.Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoLogRabbitmqApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoLogRabbitmqApplication.class, args);
    }

    @Autowired
    Producer producer;

    // 发送一条消息
    @Override
    public void run(String... args) throws Exception {
        Dto msg = new Dto();
        msg.setClas(this.getClass().getName());
        msg.setCostTime(Integer.MAX_VALUE);
        msg.setRemark("这是测试消息");
        producer.sendMsg(msg);
    }
}
