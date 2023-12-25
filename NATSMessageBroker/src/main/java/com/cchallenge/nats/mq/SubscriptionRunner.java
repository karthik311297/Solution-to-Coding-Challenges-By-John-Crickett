package com.cchallenge.nats.mq;

import java.util.List;

import com.cchallenge.nats.model.Message;
import com.cchallenge.nats.model.Subscriber;
import com.cchallenge.nats.model.Topic;

public class SubscriptionRunner implements Runnable
{
    private int offset;
    private Topic topic;
    private Subscriber subscriber;
    
    public SubscriptionRunner(Topic topic, Subscriber subscriber)
    {
        this.topic = topic;
        this.subscriber = subscriber;
        this.offset = 0;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            List<Message> messages = topic.getMessages();
            while(offset == messages.size())
            {
                System.out.println(String.format("Subscriber: %d is waiting for new messages in topic: %s", subscriber.getId(), topic.getTopicName()));
                try
                {
                    synchronized(topic)
                    {
                        topic.wait();
                    }
                }
                catch(InterruptedException e)
                {
                    System.out.println(e);
                }
            }
            Message message = messages.get(offset);
            subscriber.consumeMessage(message);
            offset++;
        }
    }
}
