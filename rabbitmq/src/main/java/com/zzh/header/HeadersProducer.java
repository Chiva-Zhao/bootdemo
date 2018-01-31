package com.zzh.header;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-1-31 13:40
 **/
public class HeadersProducer {
    private static final String EXCHANGE_NAME = "exchange_headers";

    public static void main(String[] argv) throws Exception {
        new ExchangeHeaders("exchanges type headers test msg~");
    }

    static class ExchangeHeaders {
        public ExchangeHeaders(String message) throws IOException, TimeoutException {
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
            channel.exchangeDeclare(EXCHANGE_NAME, "headers", false, true, null);

            //设置消息头键值对信息
            Map<String, Object> headers = new Hashtable<String, Object>();
            headers.put("name", "jack1");
            headers.put("age", 31);
            Builder builder = new Builder();
            builder.headers(headers);

            channel.basicPublish(EXCHANGE_NAME, "", builder.build(), message.getBytes());
            System.out.println("Sent msg is '" + message + "'");

            channel.close();
            connection.close();

        }
    }
}
