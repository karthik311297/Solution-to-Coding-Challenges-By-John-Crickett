package com.cchallenges.memcached;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class CacheManager
{
    private final ReentrantLock lock = new ReentrantLock();
    private Map<String,String> cache;
    private static final CacheManager INSTANCE = new CacheManager();
    
    private CacheManager()
    {
        cache = new ConcurrentHashMap<>();
    }
    
    public static CacheManager getInstance()
    {
        return INSTANCE;
    }
    
    public void putKeyVal(String key, String val)
    {
        lock.lock();
        cache.put(key, val);
        lock.unlock();
    }
    
    public String getVal(String key)
    {
        return cache.get(key);
    }
    
    public void removeKey(String key)
    {
        lock.lock();
        cache.remove(key);
        lock.unlock();
    }
    
    protected int getCacheSize()
    {
        return cache.size();
    }
}
