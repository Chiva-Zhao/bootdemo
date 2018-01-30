package com.zzh.fanout;

import com.rabbitmq.client.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-1-30 14:54
 **/
public class ConsumerFanout {
    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {

        ConnectionFactory cf = new ConnectionFactory();
        //rabbitmq监听IP
        cf.setHost("localhost");
        //rabbitmq默认监听端口，注意要记得开启端口
        cf.setPort(5672);

        //设置访问的用户
        cf.setUsername("test");
        cf.setPassword("test");
        //建立连接
        Connection conn = cf.newConnection();
        //创建消息通道
        Channel channel = conn.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
//        String queueName = channel.queueDeclare().getQueue();
        String queueName = channel.queueDeclare("log1",false,false,false,null).getQueue();
//        channel.queueDeclare(queueName, false, false, true, null);
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println(" Waiting for msg....");
        //创建消费者，并接受消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received is = '" + msg + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
