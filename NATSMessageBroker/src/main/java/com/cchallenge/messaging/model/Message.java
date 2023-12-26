package com.cchallenge.messaging.model;

public class Message
{
    private String messageContent;
    
    public Message(String messageContent)
    {
        this.messageContent = messageContent;
    }
    
    
    public String getMessageContent()
    {
        return messageContent;
    }
}
