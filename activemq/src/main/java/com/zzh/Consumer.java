package com.zzh;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author zhaozh
 * @version 1.0
 * @date 2018-2-3 20:04
 **/
@Component
public class Consumer {

    @JmsListener(destination = "simple.queue")
    public void receiveMsg(String msg) {
        System.out.println(msg);
    }
}
