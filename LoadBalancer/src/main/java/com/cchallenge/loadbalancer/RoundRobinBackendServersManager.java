package com.cchallenge.loadbalancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RoundRobinBackendServersManager
{
    private static Map<String,BackendServer> availableBackendServers = new HashMap<>();
    private static Map<String,BackendServer> unavailableBackendServers = new HashMap<>();
    private static LinkedList<BackendServer> activeServers = new LinkedList<>();
    
    private static List<BackendServer> allServers = new ArrayList<>();
    private static RoundRobinBackendServersManager INSTANCE = new RoundRobinBackendServersManager();
    
    public static RoundRobinBackendServersManager getInstance()
    {
        return INSTANCE;
    }
    
    public List<BackendServer> getAllServers()
    {
        return allServers;
    }
    
    public LinkedList<BackendServer> getActiveServers()
    {
        return activeServers;
    }
    
    private RoundRobinBackendServersManager()
    {
        BackendServer server1 = new BackendServer("localhost", 29001);
        availableBackendServers.put(server1.toString(), server1);
        BackendServer server2 = new BackendServer("localhost", 29002);
        availableBackendServers.put(server2.toString(), server2);
        BackendServer server3 = new BackendServer("localhost", 29003);
        availableBackendServers.put(server3.toString(), server3);
        activeServers.add(server1);
        activeServers.add(server2);
        activeServers.add(server3);
        allServers.add(server1);
        allServers.add(server2);
        allServers.add(server3);
    }
    
    public synchronized void addBackendServerBack(String backendServerAddress)
    {
        BackendServer server = unavailableBackendServers.get(backendServerAddress);
        if(server == null) return;
        unavailableBackendServers.remove(server.toString());
        if(!activeServers.contains(server))
        {
            activeServers.add(server);
        }
        availableBackendServers.put(server.toString(), server);
    }
    
    public synchronized void removeBackendServer(String backendServerAddress)
    {
        BackendServer server = availableBackendServers.get(backendServerAddress);
        if(server == null) return;
        availableBackendServers.remove(server.toString());
        if(activeServers.contains(server))
        {
            activeServers.remove(server);
        }
        unavailableBackendServers.put(server.toString(), server);
    }
    
    public BackendServer getHead()
    {
        return activeServers.peekFirst();
    }
    
    public synchronized void moveHead()
    {
        BackendServer server = activeServers.pollFirst();
        activeServers.add(server);
    }
}
