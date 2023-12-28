package com.cchallenge.nats;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cchallenge.nats.Constants.Commands;

public class SocketConnectionManager
{
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    public static void handleClientConnection(Socket socket)
    {
        executorService.submit(() -> {
                    try
                    {
                        boolean done = false;
                        Integer subscriberId = null;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bufferedWriter.write("-------------------------------------------------------------" + System.lineSeparator());
                        bufferedWriter.write("Welcome to NATS Message Broker." + System.lineSeparator());
                        bufferedWriter.write("-------------------------------------------------------------" + System.lineSeparator());
                        bufferedWriter.flush();
                        String clientRequest = null;
                        while(!done && (clientRequest = bufferedReader.readLine()) != null)
                        {
                            if(clientRequest.equalsIgnoreCase(Commands.STOP.toString()))
                            {
                                System.out.println(String.format("Client %d has disconnected", Thread.currentThread().getId()));
                                bufferedWriter.write(String.format("Disconnected%s", System.lineSeparator()));
                                bufferedWriter.flush();
                                done = true;
                                if(subscriberId != null)
                                {
                                    SubscribersManager.getInstance().removeSubscriber(subscriberId);
                                    SubscribersManager.getInstance().removeSubscriberBuffer(subscriberId);
                                }
                            }
                            else if(clientRequest.equalsIgnoreCase(Commands.CONNECT.toString()))
                            {
                                subscriberId = SubscribersManager.getInstance().createNewSubscriber(bufferedWriter);
                                bufferedWriter.write(String.format("+OK%s", System.lineSeparator()));
                                bufferedWriter.flush();
                            }
                            else
                            {
                                if(subscriberId == null)
                                {
                                    bufferedWriter.write(String.format("You Need to Connect first %s", System.lineSeparator()));
                                    bufferedWriter.flush();
                                }
                                else
                                {
                                    bufferedWriter.write(String.format("%s%s",
                                            CommandHandler.handleCommand(clientRequest.trim(), subscriberId), System.lineSeparator()));
                                    bufferedWriter.flush();
                                }
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
