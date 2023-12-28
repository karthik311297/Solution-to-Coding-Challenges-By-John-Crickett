package com.cchallenge.nats;

import java.io.BufferedWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.cchallenge.messaging.model.Subscriber;

public class SubscribersManager
{
    private AtomicInteger id;
    private Map<Integer,Subscriber> subscriberMap;
    private Map<Integer,BufferedWriter> subscriberBufferMap;
    private static final SubscribersManager INSTANCE = new SubscribersManager();
    
    public static SubscribersManager getInstance()
    {
        return INSTANCE;
    }
    
    private SubscribersManager()
    {
        id = new AtomicInteger(1);
        subscriberMap = new ConcurrentHashMap<>();
        subscriberBufferMap = new ConcurrentHashMap<>();
    }
    
    public int createNewSubscriber(BufferedWriter bufferedWriter)
    {
        int newId = id.getAndIncrement();
        Subscriber subscriber = new Subscriber(newId);
        subscriberMap.put(newId, subscriber);
        subscriberBufferMap.put(newId, bufferedWriter);
        return newId;
    }
    
    public Subscriber getSubscriber(int subscriberID)
    {
        return subscriberMap.get(subscriberID);
    }
    
    public BufferedWriter getSubscriberBuffer(int subscriberID)
    {
        return subscriberBufferMap.get(subscriberID);
    }
    
    public void removeSubscriber(int subscriberID)
    {
        subscriberMap.remove(subscriberID);
    }
    
    public void removeSubscriberBuffer(int subscriberID)
    {
        subscriberBufferMap.remove(subscriberID);
    }
    
    // Exposed for Testing
    protected int getNumSubscribers()
    {
        return subscriberMap.size();
    }
}
