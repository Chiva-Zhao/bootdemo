package com.zzh;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

@SpringBootApplication
@EnableJms
public class ActivemqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqApplication.class, args);
    }

    @Bean
    public Topic topic(){
        return new ActiveMQTopic("simple.topic");
    }
    @Bean
    public Queue destOne() {
        return new ActiveMQQueue("simple.queue");
    }
}
