package com.cchallenge.loadbalancer;

import java.util.ArrayList;
import java.util.List;

public class HealthCheckRunner implements Runnable
{
    @Override
    public void run()
    {
        RoundRobinBackendServersManager manager = RoundRobinBackendServersManager.getInstance();
        List<BackendServer> backendServers = manager.getAllServers();
        List<String> backendServerAddressesWhichAreDown = new ArrayList<>();
        List<String> backendServersWhichAreUp = new ArrayList<>();
        for(BackendServer server : backendServers)
        {
            HealthChecker.STATUS status = HealthChecker.getHealthStatusOfServer(server);
            if(status == HealthChecker.STATUS.DOWN) backendServerAddressesWhichAreDown.add(server.toString());
            else backendServersWhichAreUp.add(server.toString());
        }
        for(String server : backendServerAddressesWhichAreDown)
        {
            System.out.println(server + " -- is down");
            manager.removeBackendServer(server);
        }
        for(String server : backendServersWhichAreUp)
        {
            System.out.println(server + " -- is up");
            manager.addBackendServerBack(server);
        }
    }
}
