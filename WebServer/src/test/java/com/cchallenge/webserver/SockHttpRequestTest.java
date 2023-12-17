package com.cchallenge.webserver;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SockHttpRequestTest
{
    @Test
    public void shouldGetRightHttpMethodAndPathFromRequest()
    {
        String requestString = " GET /hello HTTP/1.1";
        
        SockHttpRequest sockHttpRequest = SockHttpRequest.build(requestString);
        
        Assert.assertEquals(sockHttpRequest.getMethod(), SockHttpRequest.METHOD.GET);
        Assert.assertEquals(sockHttpRequest.getRequestedPath(), "/hello");
    }
}