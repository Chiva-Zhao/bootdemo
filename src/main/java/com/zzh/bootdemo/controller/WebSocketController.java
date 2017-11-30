package com.zzh.bootdemo.controller;

import com.zzh.bootdemo.model.WsGreeting;
import com.zzh.bootdemo.model.WsMsg;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("hello")
    @SendTo("/topic/greetings")
    public WsGreeting greeting(WsMsg message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new WsGreeting("Hello, " + message.getName() + "!");
    }

}
