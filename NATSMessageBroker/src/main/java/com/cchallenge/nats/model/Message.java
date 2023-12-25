package com.cchallenge.nats.model;

public class Message
{
    private int messageId;
    private String messageContent;
    
    public Message(int messageId, String messageContent)
    {
        this.messageId = messageId;
        this.messageContent = messageContent;
    }
    
    public int getMessageId()
    {
        return messageId;
    }
    
    public String getMessageContent()
    {
        return messageContent;
    }
}
