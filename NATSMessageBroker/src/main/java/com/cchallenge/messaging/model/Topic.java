package com.cchallenge.messaging.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Topic
{
    private String topicName;
    private List<Message> messages;
    private Set<Subscriber> subscribers;
    
    public Topic(String topicName)
    {
        this.topicName = topicName;
        this.messages = new ArrayList<>();
        this.subscribers = new HashSet<>();
    }
    
    public String getTopicName()
    {
        return topicName;
    }
    
    public List<Message> getMessages()
    {
        return messages;
    }
    
    public Set<Subscriber> getSubscribers()
    {
        return subscribers;
    }
    
    public boolean isSubscribing(Subscriber subscriber)
    {
        return getSubscribers().contains(subscriber);
    }
}
