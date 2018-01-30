package com.zzh.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-1-30 16:39
 **/
public class ProducerFanout {
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

        //声明路由名字和类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String message = makeMessage(args);
        //发送消息
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println("send msg is " + message);
        channel.close();
        conn.close();
    }

    private static String makeMessage(String[] strings) {
        if (strings.length < 1) {
            return "这是默认消息！！";
        } else {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < strings.length; i++) {
                buffer.append(strings[i]);
            }
            return buffer.toString();
        }
    }
}
