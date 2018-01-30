package com.zzh.basic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class Producer01 {
    private final static String QUEUE_NAME = "hello";

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
        String msg = "hello world!!!! 你好啊~";
        //创建hello队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发送消息
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("send msg " + msg + " to [" + QUEUE_NAME + "] queue !");
        channel.close();
        conn.close();
    }
}
