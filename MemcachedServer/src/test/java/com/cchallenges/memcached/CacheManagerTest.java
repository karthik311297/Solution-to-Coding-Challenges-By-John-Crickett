package com.cchallenges.memcached;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CacheManagerTest
{
    private static final int NUM_THREADS = 30;
    
    @Test
    public void shouldTestConcurrencyOfPutOperation() throws InterruptedException
    {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch countDownLatch = new CountDownLatch(NUM_THREADS);
        for(int i = 0; i < NUM_THREADS; i++)
        {
            int finalI = i;
            executorService.submit(() ->
            {
                try
                {
                    if(finalI%2 == 0) Thread.sleep(1000);
                    if(finalI%2 != 0) Thread.sleep(500);
                    CacheManager.getInstance().putKeyVal("key" + finalI, "val" + finalI);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                finally
                {
                    countDownLatch.countDown();
                }
            });
        }
        
        countDownLatch.await();
        Assert.assertEquals(CacheManager.getInstance().getCacheSize(), NUM_THREADS);
        
    }
}