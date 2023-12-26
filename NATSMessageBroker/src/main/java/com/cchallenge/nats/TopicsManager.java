package com.cchallenge.nats;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.cchallenge.messaging.model.Topic;
import com.cchallenge.messaging.mq.MessageQueue;

public class TopicsManager
{
    private Map<String,Topic> topicsMap;
    private static final TopicsManager INSTANCE = new TopicsManager();
    
    public static TopicsManager getInstance()
    {
        return INSTANCE;
    }
    
    private TopicsManager()
    {
        topicsMap = new ConcurrentHashMap<>();
    }
    
    public void createNewTopic(String topicName)
    {
        Topic topic = new Topic(topicName);
        MessageQueue.getInstance().createTopic(topicName);
        topicsMap.put(topicName, topic);
    }
    
    // Exposed for Testing
    protected int getNumTopics()
    {
        return topicsMap.size();
    }
}
