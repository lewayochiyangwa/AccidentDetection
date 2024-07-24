package com.example.finalyearproject.models;

public class SmsMessage {

    public SmsMessage(String body, String from, String to) {
        this.body = body;
        this.from = from;
        this.to = to;
    }

    public SmsMessage() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String body;
    public String from;
    public String  to;

}
