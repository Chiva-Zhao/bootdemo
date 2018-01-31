package com.zzh.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-1-31 9:20
 **/
public class TopicConsumer {
    private static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] argv) throws IOException, TimeoutException {

        new ExchangeTopic("logs.info");
        new ExchangeTopic("logs.*");
        new ExchangeTopic("logs.#");
    }

    static class ExchangeTopic {
        public ExchangeTopic(final String routingKey) throws IOException, TimeoutException {
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
            //队列名称
            String queueName = routingKey + ".queue";
            //创建队列
            channel.queueDeclare(queueName, false, false, true, null);
            //把队列绑定到路由上
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);

            System.out.println(" [routingKey = " + routingKey + "] Waiting for msg....");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");

                    System.out.println("[routingKey = " + routingKey + "] Received msg is '" + message + "'");
                }
            };
            channel.basicConsume(queueName, true, consumer);
        }

    }
}
