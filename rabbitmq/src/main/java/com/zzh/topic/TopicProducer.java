package com.zzh.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-1-31 12:29
 **/
public class TopicProducer {
    private static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] argv) throws Exception {
        new ExchangeTopic("logs.info", "logs Info test ！！");
        new ExchangeTopic("logs.error", "logs error test ！！");
        new ExchangeTopic("logs.error.toc", "logs error toc test ！！");
    }

    static class ExchangeTopic {
        public ExchangeTopic(String routingKey, String message) throws IOException, TimeoutException {
            ConnectionFactory factory = new ConnectionFactory();
            //rabbitmq监听IP
            factory.setHost("localhost");
            //rabbitmq监听默认端口
            factory.setPort(5672);
            //设置访问的用户
            factory.setUsername("test");
            factory.setPassword("test");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            //声明路由名字和类型
            channel.exchangeDeclare(EXCHANGE_NAME, "topic", false, true, null);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println("[routingKey = " + routingKey + "] Sent msg is '" + message + "'");

            channel.close();
            connection.close();

        }
    }
}
