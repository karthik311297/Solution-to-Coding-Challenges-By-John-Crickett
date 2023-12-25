package com.cchallenge.nats.mq;

import com.cchallenge.nats.model.Message;
import com.cchallenge.nats.model.Subscriber;
import com.cchallenge.nats.model.Topic;

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
}
