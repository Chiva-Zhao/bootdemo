package com.zzh.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-1-30 18:00
 **/
public class DirectProducer {
    private static final String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] argv) throws Exception {
        new ExchangeDirect("logs.info", "logs Info test ！！");
        new ExchangeDirect("logs.error", "logs error test ！！");
        new ExchangeDirect("logs.warning", "logs warning test ！！");
    }

    static class ExchangeDirect {
        public ExchangeDirect(String routingKey, String message) throws IOException, TimeoutException {
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

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println("[routingKey = " + routingKey + "] Sent msg is '" + message + "'");

            channel.close();
            connection.close();

        }

    }
}
