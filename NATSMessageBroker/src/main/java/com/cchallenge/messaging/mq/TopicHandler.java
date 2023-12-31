package com.cchallenge.messaging.mq;

import com.cchallenge.messaging.model.Message;
import com.cchallenge.messaging.model.Subscriber;
import com.cchallenge.messaging.model.Topic;

public class TopicHandler
{
    private Topic topic;
    
    public TopicHandler(Topic topic)
    {
        this.topic = topic;
    }
    
    public void addMessage(Message message)
    {
        synchronized(this)
        {
            topic.getMessages().add(message);
            System.out.println(String.format("Published message to topic: %s", topic.getTopicName()));
        }
    }
    
    public void addSubscriber(Subscriber subscriber)
    {
        synchronized(this)
        {
            topic.getSubscribers().add(subscriber);
            System.out.println(String.format("Subscriber: %d has started subscribing to topic: %s", subscriber.getId(), topic.getTopicName()));
        }
    }
    
    public void removeSubscriber(Subscriber subscriber)
    {
        synchronized(this)
        {
            topic.getSubscribers().remove(subscriber);
            System.out.println(String.format("Subscriber: %d has stopped subscribing to topic: %s", subscriber.getId(), topic.getTopicName()));
        }
    }
}
