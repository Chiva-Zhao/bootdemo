package com.zzh.bootdemo.model;

public class JmsEmail {

    private String to;
    private String body;

    public JmsEmail() {
    }

    public JmsEmail(String to, String body) {
        this.to = to;
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("JmsEmail{to=%s, body=%s}", getTo(), getBody());
    }

}