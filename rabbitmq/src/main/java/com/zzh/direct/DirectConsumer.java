package com.zzh.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-1-30 17:51
 **/
public class DirectConsumer {
    private static final String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] argv) throws IOException, TimeoutException {

        new ExchangeDirect("logs.info");
        new ExchangeDirect("logs.error");

    }

    static class ExchangeDirect {
        public ExchangeDirect(String routingKey) throws IOException, TimeoutException {
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
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", false, true, null);
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

                    System.out.println("[routingKey = " + envelope.getRoutingKey() + "] Received msg is '" + message + "'");
                }
            };
            channel.basicConsume(queueName, true, consumer);
        }

    }
}
