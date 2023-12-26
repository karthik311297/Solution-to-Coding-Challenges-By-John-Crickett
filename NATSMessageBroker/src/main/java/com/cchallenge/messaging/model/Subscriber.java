package com.cchallenge.messaging.model;

public class Subscriber
{
    private int id;
    
    public Subscriber(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String consumeMessage(Message message)
    {
        sleep(1000);
        System.out.println(String.format("Subscriber: %d, is consuming message: %s",
                id, message.getMessageContent()));
        sleep(1000);
        System.out.println(String.format("Subscriber: %d, has consumed message: %s",
                id, message.getMessageContent()));
        return message.getMessageContent();
    }
    
    private void sleep(long millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch(InterruptedException e)
        {
            System.out.println(String.format("Subscriber: %d, has got this error: %s", id, e));
        }
    }
}
