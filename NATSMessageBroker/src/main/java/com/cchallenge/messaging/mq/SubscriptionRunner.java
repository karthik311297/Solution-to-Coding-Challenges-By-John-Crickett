package com.cchallenge.messaging.mq;

import java.io.IOException;
import java.util.List;

import com.cchallenge.messaging.model.Message;
import com.cchallenge.messaging.model.Subscriber;
import com.cchallenge.messaging.model.Topic;
import com.cchallenge.nats.SubscribersManager;

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
            String consumedMessage = subscriber.consumeMessage(message);
            writeToSocket(consumedMessage);
            offset++;
        }
    }
    
    private void writeToSocket(String consumedMessage)
    {
        try
        {
            SubscribersManager.getInstance().getSubscriberBuffer(subscriber.getId()).write(String.format("%s%s", String.format("MSG %s %s",
                    topic.getTopicName(), consumedMessage), System.lineSeparator()));
            SubscribersManager.getInstance().getSubscriberBuffer(subscriber.getId()).flush();
        }
        catch(Exception e)
        {
            System.out.println("Connection is already closed");
        }
    }
}
