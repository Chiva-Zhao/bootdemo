package com.zzh.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-2-3 20:01
 **/
@Component
public class Producer implements CommandLineRunner {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;

    @Override
    public void run(String... strings) throws Exception {
        send("Sample message");
    }

    private void send(String sample_message) {
        jmsMessagingTemplate.convertAndSend(this.queue, sample_message);
    }
}
