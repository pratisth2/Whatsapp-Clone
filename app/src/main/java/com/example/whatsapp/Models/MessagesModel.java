package com.example.whatsapp.Models;

public class MessagesModel {
    String uId, message,messagId;
    Long timestamp;

    public MessagesModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

    public MessagesModel(String uId, String message, Long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;

    }
    public MessagesModel(){}

    public String getMessagId() {
        return messagId;
    }

    public void setMessagId(String messagId) {
        this.messagId = messagId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
