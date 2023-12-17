package com.cchallenge.loadbalancer;

import java.util.Objects;

public class BackendServer
{
    private String address;
    private int port;
    
    public BackendServer(String address, int port)
    {
        this.address = address;
        this.port = port;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public int getPort()
    {
        return port;
    }
    
    @Override
    public String toString()
    {
        return "http://" + address + ":" + port;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof BackendServer)) return false;
        BackendServer server = (BackendServer)o;
        return getPort() == server.getPort() && getAddress().equals(server.getAddress());
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(getAddress(), getPort());
    }
}
