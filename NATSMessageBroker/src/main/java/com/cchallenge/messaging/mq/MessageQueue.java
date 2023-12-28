package com.cchallenge.messaging.mq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cchallenge.messaging.model.Message;
import com.cchallenge.messaging.model.Subscriber;
import com.cchallenge.messaging.model.Topic;

public class MessageQueue
{
    private Map<String,Topic> topics;
    private Map<String,TopicHandler> topicHandlers;
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
    
    public void createTopic(String topicName)
    {
        Topic topic = new Topic(topicName);
        topics.put(topic.getTopicName(), topic);
        topicHandlers.computeIfAbsent(topicName, tId -> new TopicHandler(topic));
    }
    
    public void publishMessageToTopic(String topicName, Message message)
    {
        Topic topic = topics.get(topicName);
        TopicHandler handler = topicHandlers.get(topicName);
        handler.addMessage(message);
        synchronized(topic)
        {
            topic.notifyAll();
        }
    }
    
    public void subscribeToTopic(String topicName, Subscriber subscriber)
    {
        Topic topic = topics.get(topicName);
        TopicHandler handler = topicHandlers.get(topicName);
        handler.addSubscriber(subscriber);
        SubscriptionRunner subscriptionRunner = new SubscriptionRunner(topic, subscriber);
        new Thread(subscriptionRunner).start();
    }
    
    public void unsubscribeToTopic(String topicName, Subscriber subscriber)
    {
        TopicHandler handler = topicHandlers.get(topicName);
        handler.removeSubscriber(subscriber);
    }
}
