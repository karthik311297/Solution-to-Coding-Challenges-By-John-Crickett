package com.cchallenge.nats.model;

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
    
    public void consumeMessage(Message message)
    {
        sleep(1000);
        System.out.println(String.format("Subscriber: %d, has consumed message: %d which has the content: %s",
                id, message.getMessageId(), message.getMessageContent()));
        sleep(1000);
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
