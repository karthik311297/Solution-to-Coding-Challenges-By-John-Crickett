package com.cchallenge.nats.model;

import java.util.ArrayList;
import java.util.List;

public class Topic
{
    private int topicId;
    private String topicName;
    private List<Message> messages;
    private List<Subscriber> subscribers;
    
    public Topic(String topicName, int topicId)
    {
        this.topicId = topicId;
        this.topicName = topicName;
        this.messages = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }
    
    public int getTopicId()
    {
        return topicId;
    }
    
    public String getTopicName()
    {
        return topicName;
    }
    
    public List<Message> getMessages()
    {
        return messages;
    }
    
    public List<Subscriber> getSubscribers()
    {
        return subscribers;
    }
}
