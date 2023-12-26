package com.cchallenge.messaging.model;

import java.util.ArrayList;
import java.util.List;

public class Topic
{
    private String topicName;
    private List<Message> messages;
    private List<Subscriber> subscribers;
    
    public Topic(String topicName)
    {
        this.topicName = topicName;
        this.messages = new ArrayList<>();
        this.subscribers = new ArrayList<>();
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
