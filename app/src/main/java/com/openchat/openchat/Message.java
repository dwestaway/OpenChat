package com.openchat.openchat;

/**
 * Created by Dan on 14/03/2018.
 */

public class Message {

    private  String content;

    public Message() {

    }
    public Message(String content) {
        this.content = content;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
