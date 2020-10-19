package com.example.dorm;

public class Message {

    private String sendTo;
    private String receiveFrom;
    private String subject;
    private String content;


    public Message() {
    }

    public Message(String sendTo, String receiveFrom, String subject, String content) {
        this.sendTo = sendTo;
        this.receiveFrom = receiveFrom;
        this.subject = subject;
        this.content = content;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getReceiveFrom() {
        return receiveFrom;
    }

    public void setReceiveFrom(String receiveFrom) {
        this.receiveFrom = receiveFrom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
