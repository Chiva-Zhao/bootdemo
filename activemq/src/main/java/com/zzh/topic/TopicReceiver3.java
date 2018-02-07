package com.zzh.topic;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-2-4 16:36
 **/
@Component
public class TopicReceiver3 {
    @JmsListener(destination = "simple.topic")
    public void receiveTopic(String text) {
        System.out.println("TopicReceiver3=" + text);
    }
}
