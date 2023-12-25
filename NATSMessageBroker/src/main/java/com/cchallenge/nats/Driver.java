package com.cchallenge.nats;

import com.cchallenge.nats.model.Message;
import com.cchallenge.nats.model.Subscriber;
import com.cchallenge.nats.mq.MessageQueue;

public class Driver
{
    public static void main(String[] args) throws InterruptedException
    {
        MessageQueue messageQueue = MessageQueue.getInstance();
        Subscriber subscriber1 = new Subscriber(1);
        Subscriber subscriber2 = new Subscriber(2);
        Subscriber subscriber3 = new Subscriber(3);
        messageQueue.createTopic(1, "Topic1");
        messageQueue.subscribeToTopic(1, subscriber1);
        messageQueue.createTopic(2, "Topic2");
        Thread.sleep(2000);
        messageQueue.publishMessageToTopic(1, new Message(1, "message1topic1"));
        messageQueue.subscribeToTopic(1, subscriber2);
        messageQueue.publishMessageToTopic(2, new Message(2, "message1topic2"));
        Thread.sleep(2000);
        messageQueue.subscribeToTopic(2, subscriber1);
        messageQueue.createTopic(3, "Topic3");
        messageQueue.publishMessageToTopic(3, new Message(3, "message1topic3"));
        Thread.sleep(2000);
        messageQueue.subscribeToTopic(3, subscriber3);
        Thread.sleep(2000);
        messageQueue.publishMessageToTopic(1, new Message(4, "message2topic1"));
        messageQueue.publishMessageToTopic(3, new Message(5, "message2topic3"));
    }
}
