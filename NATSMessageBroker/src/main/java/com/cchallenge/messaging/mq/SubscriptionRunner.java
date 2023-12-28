package com.cchallenge.messaging.mq;

import java.io.BufferedWriter;
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
            BufferedWriter bufferedWriter;
            List<Message> messages = topic.getMessages();
            while(offset == messages.size())
            {
                bufferedWriter = SubscribersManager.getInstance().getSubscriberBuffer(subscriber.getId());
                if(shouldStopSubscription(bufferedWriter)) break;
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
            bufferedWriter = SubscribersManager.getInstance().getSubscriberBuffer(subscriber.getId());
            if(shouldStopSubscription(bufferedWriter))
            {
                System.out.println(String.format("Subscriber: %d has disconnected or is no longer subscribing to topic: %s," +
                        " so stopping its subscription", subscriber.getId(), topic.getTopicName()));
                break;
            }
            Message message = messages.get(offset);
            String consumedMessage = subscriber.consumeMessage(message);
            writeToSocket(consumedMessage, bufferedWriter);
            offset++;
        }
    }
    
    private boolean shouldStopSubscription(BufferedWriter bufferedWriter)
    {
        return bufferedWriter == null || (!topic.isSubscribing(subscriber));
    }
    
    private void writeToSocket(String consumedMessage, BufferedWriter bufferedWriter)
    {
        try
        {
            bufferedWriter.write(String.format("%s%s", String.format("MSG %s %s",
                    topic.getTopicName(), consumedMessage), System.lineSeparator()));
            bufferedWriter.flush();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
