package com.cchallenges.memcached;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketConnectionManager
{
    private static final String STOP_COMMAND = "STOP";
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    public static void handleClientConnection(Socket socket)
    {
        executorService.submit(() -> {
                    try
                    {
                        boolean done = false;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bufferedWriter.write("-------------------------------------------------------------" + System.lineSeparator());
                        bufferedWriter.write("Welcome to MEMCACHED Server. Commands Supported: GET, PUT, REMOVE AND STOP" + System.lineSeparator());
                        bufferedWriter.write("-------------------------------------------------------------" + System.lineSeparator());
                        bufferedWriter.flush();
                        String clientRequest = null;
                        while(!done && (clientRequest = bufferedReader.readLine()) != null)
                        {
                            if(clientRequest.equalsIgnoreCase(Commands.STOP.toString()))
                            {
                                System.out.printf("Client %d has disconnected%n", Thread.currentThread().getId());
                                bufferedWriter.write(String.format("Disconnected%s", System.lineSeparator()));
                                bufferedWriter.flush();
                                done = true;
                            }
                            else
                            {
                                bufferedWriter.write(String.format("You ran this command:%s, %s.. %s",
                                        clientRequest, CommandHandler.handleCommand(clientRequest.trim()), System.lineSeparator()));
                                bufferedWriter.flush();
                            }
                        }
                        bufferedReader.close();
                        bufferedWriter.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println(SocketConnectionManager.class.getSimpleName() + " :" + e);
                    }
                    finally
                    {
                        closeConnection(socket);
                    }
                }
        );
    }
    
    private static void closeConnection(Socket socket)
    {
        try
        {
            socket.close();
        }
        catch(IOException e)
        {
            System.out.println(SocketConnectionManager.class.getSimpleName() + " :" + e);
        }
    }
}
