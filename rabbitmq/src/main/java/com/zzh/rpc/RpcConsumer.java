package com.zzh.rpc;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-2-3 18:40
 **/
public class RpcConsumer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] argv) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        //rabbitmq监听IP
        factory.setHost("localhost");
        //rabbitmq监听默认端口
        factory.setPort(5672);
        //设置访问的用户
        factory.setUsername("test");
        factory.setPassword("test");

        Connection connection = null;
        try {
            connection = factory.newConnection();
            final Channel channel = connection.createChannel();
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            System.out.println("等待接受producer消息....");
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(properties.getCorrelationId())
                            .build();
                    String response = "";
                    try {
                        String message = new String(body, "UTF-8");
                        System.out.println("consumer 接收的消息是：" + message);
                        response = handleMsg(message);
                        System.out.println("consumer 发送的消息是：" + response);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    } finally {
                        channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };
            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
        } catch (Exception e) {
            connection.close();
            e.printStackTrace();
        }
    }

    private static String handleMsg(String msg) {
        Date date = new Date();
        String response = "";
        switch (msg) {
            case "时间":
                response = new SimpleDateFormat("HH:mm").format(date);
                break;
            case "日期":
                response = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(date);
                break;
            default:
                response = "未知信息";
                break;
        }
        return response;
    }
}
