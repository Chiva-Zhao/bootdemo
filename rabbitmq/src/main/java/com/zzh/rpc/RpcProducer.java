package com.zzh.rpc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-2-3 18:46
 **/
public class RpcProducer {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;

    public static void main(String[] args) throws Exception {
        RpcProducer producer = new RpcProducer();
        producer.call("时间");
        producer.call("日期");
        producer.call("什么都不是~");
        //producer.close();
    }

    public RpcProducer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        //rabbitmq监听IP
        factory.setHost("localhost");
        //rabbitmq监听默认端口
        factory.setPort(5672);
        //设置访问的用户
        factory.setUsername("test");
        factory.setPassword("test");

        connection = factory.newConnection();
        channel = connection.createChannel();

        //生成一个临时的接受队列名
        replyQueueName = channel.queueDeclare().getQueue();
    }

    public void call(String message) throws Exception {

        //生成一个唯一的字符串
        final String corrId = UUID.randomUUID().toString();

        //将corrId、replyQueueName打包发送给consumer
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        System.out.println("producer 发送的消息是 :" + message);
        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //获取corrId相同的消息
                if (properties.getCorrelationId().equals(corrId)) {
                    System.out.println("producer 接收的消息是 :" + new String(body, "UTF-8"));
                }
            }
        });

    }

    public void close() throws Exception {
        channel.close();
        connection.close();
    }
}
