package com.zzh.topic;

import org.apache.activemq.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-2-4 16:34
 **/
@Component
public class TopicSender implements CommandLineRunner {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Topic topic;

    public void send(String message) {
        jmsMessagingTemplate.convertAndSend(topic, message);
    }

    @Override
    public void run(String... strings) throws Exception {
        send("send msg to topic");
    }
}
