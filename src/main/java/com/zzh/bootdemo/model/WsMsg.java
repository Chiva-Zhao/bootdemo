package com.zzh.bootdemo.model;

public class WsMsg {
    public WsMsg() {
    }

    public WsMsg(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
