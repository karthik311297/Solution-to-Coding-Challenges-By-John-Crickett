package com.cchallenge.webserver;

public class SockHttpRequest
{
    public enum METHOD
    {
        GET,
        POST
    }
    
    private METHOD method;
    private String requestedPath;
    
    private SockHttpRequest(METHOD method, String requestedPath)
    {
        this.method = method;
        this.requestedPath = requestedPath;
    }
    
    public static SockHttpRequest build(String requestString)
    {
        int startIndex = requestString.indexOf("GET") + 4;
        int endIndex = requestString.indexOf("HTTP") - 1;
        return new SockHttpRequest(METHOD.GET, requestString.substring(startIndex, endIndex));
    }
    
    public METHOD getMethod()
    {
        return method;
    }
    
    public String getRequestedPath()
    {
        return requestedPath;
    }
}
