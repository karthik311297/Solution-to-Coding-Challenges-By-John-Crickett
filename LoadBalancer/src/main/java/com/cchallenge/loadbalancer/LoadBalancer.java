package com.cchallenge.loadbalancer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoadBalancer
{
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    
    public static void main(String[] args) throws IOException
    {
        int port = 29000;
        ServerSocket server = new ServerSocket(port);
        scheduledExecutorService.scheduleAtFixedRate(new HealthCheckRunner(), 0, 10, TimeUnit.SECONDS);
        System.out.println("Server listening on port: " + port);
        while(true)
        {
            Socket socket = server.accept();
            handleClientRequestInThread(socket);
        }
    }
    
    private static void handleClientRequestInThread(Socket socket)
    {
        executorService.submit(() -> {
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String clientRequest = bufferedReader.readLine();
                String response = makeCallToBackendServer(clientRequest);
                bufferedWriter.write(response);
                bufferedWriter.flush();
                bufferedWriter.close();
                bufferedReader.close();
                socket.close();
                System.out.println("Load balancer has responded response:" + response);
            }
            catch(Exception e)
            {
                System.out.println(LoadBalancer.class.getSimpleName() + " :" + e);
            }
        });
    }
    
    private static String makeCallToBackendServer(String clientRequest) throws IOException
    {
        BackendServer server = RoundRobinBackendServersManager.getInstance().getHead();
        Socket socket = new Socket(server.getAddress(), server.getPort());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write(clientRequest + "\r");
        bufferedWriter.flush();
        RoundRobinBackendServersManager.getInstance().moveHead();
        StringBuilder response = new StringBuilder();
        String line = null;
        while((line = bufferedReader.readLine()) != null)
        {
            response.append(line + "\r\n");
        }
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
        System.out.println("Request sent to: " + server);
        return response.toString();
    }
}
