package com.cchallenge.nats.mq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cchallenge.nats.model.Message;
import com.cchallenge.nats.model.Subscriber;
import com.cchallenge.nats.model.Topic;

public class MessageQueue
{
    private Map<Integer,Topic> topics;
    private Map<Integer,TopicHandler> topicHandlers;
    private static final MessageQueue INSTANCE = new MessageQueue();
    
    public static MessageQueue getInstance()
    {
        return INSTANCE;
    }
    
    public MessageQueue()
    {
        this.topics = new ConcurrentHashMap<>();
        this.topicHandlers = new ConcurrentHashMap<>();
    }
    
    public void createTopic(int topicId, String topicName)
    {
        Topic topic = new Topic(topicName, topicId);
        topics.put(topic.getTopicId(), topic);
        topicHandlers.computeIfAbsent(topicId, tId -> new TopicHandler(topic));
    }
    
    public void publishMessageToTopic(int topicId, Message message)
    {
        Topic topic = topics.get(topicId);
        TopicHandler handler = topicHandlers.get(topicId);
        handler.addMessage(message);
        synchronized(topic)
        {
            topic.notifyAll();
        }
    }
    
    public void subscribeToTopic(int topicId, Subscriber subscriber)
    {
        Topic topic = topics.get(topicId);
        TopicHandler handler = topicHandlers.get(topicId);
        handler.addSubscriber(subscriber);
        SubscriptionRunner subscriptionRunner = new SubscriptionRunner(topic, subscriber);
        new Thread(subscriptionRunner).start();
    }
}
