package com.zzh.bootdemo.model;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver {
    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMsg(Email email) {
        System.out.println("Receive <" + email + ">");
    }
}
