package com.cchallenge.messaging;

import com.cchallenge.messaging.model.Message;
import com.cchallenge.messaging.model.Subscriber;
import com.cchallenge.messaging.mq.MessageQueue;

public class Driver
{
    public static void main(String[] args) throws InterruptedException
    {
        MessageQueue messageQueue = MessageQueue.getInstance();
        Subscriber subscriber1 = new Subscriber(1);
        Subscriber subscriber2 = new Subscriber(2);
        Subscriber subscriber3 = new Subscriber(3);
        messageQueue.createTopic("Topic1");
        messageQueue.subscribeToTopic("Topic1", subscriber1);
        messageQueue.createTopic("Topic2");
        Thread.sleep(2000);
        messageQueue.publishMessageToTopic("Topic1", new Message("message1topic1"));
        messageQueue.subscribeToTopic("Topic1", subscriber2);
        messageQueue.publishMessageToTopic("Topic2", new Message("message1topic2"));
        Thread.sleep(2000);
        messageQueue.subscribeToTopic("Topic2", subscriber1);
        messageQueue.createTopic("Topic3");
        messageQueue.publishMessageToTopic("Topic3", new Message("message1topic3"));
        Thread.sleep(2000);
        messageQueue.subscribeToTopic("Topic3", subscriber3);
        Thread.sleep(2000);
        messageQueue.publishMessageToTopic("Topic1", new Message("message2topic1"));
        messageQueue.publishMessageToTopic("Topic3", new Message("message2topic3"));
    }
}
