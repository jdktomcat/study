package jdktomcat.demo.producer;


import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.apache.rocketmq.shaded.org.slf4j.Logger;
import org.apache.rocketmq.shaded.org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Hello world!
 */
public class RocketmqProducer {
    private static final Logger logger = LoggerFactory.getLogger(RocketmqProducer.class);

    public static void main(String[] args) throws ClientException {
        String endpoint = "172.31.184.43:10911";
        String topic = "TestTopic";
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(endpoint);
        ClientConfiguration configuration = builder.build();
        Producer producer = provider.newProducerBuilder().setTopics(topic)
                .setClientConfiguration(configuration).build();
        Message message = provider.newMessageBuilder()
                .setTopic(topic).setKeys("messageKey")
                .setTag("messageTag").setBody("messageBody".getBytes())
                .build();
        try {
            SendReceipt sendReceipt = producer.send(message);
            logger.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        } catch (ClientException e) {
            logger.error("Failed to send message", e);
        }
    }
}
