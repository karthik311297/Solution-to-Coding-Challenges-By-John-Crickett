package com.cchallenge.webserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server
{
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);
    
    public static void main(String[] args) throws IOException
    {
        int port = Integer.parseInt(args[0]);
        ServerSocket server = new ServerSocket(port);
        System.out.println("Backend Server listening on port: " + port);
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
                String clientRequest = bufferedReader.readLine();
                SockHttpRequest sockHttpRequest = SockHttpRequest.build(clientRequest);
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n " + sockHttpRequest.getRequestedPath() + "\r";
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(httpResponse);
                bufferedWriter.flush();
                bufferedWriter.close();
                bufferedReader.close();
                socket.close();
                System.out.println("Backend Server has responded to: " + clientRequest);
            }
            catch(Exception e)
            {
                System.out.println(Server.class.getSimpleName() + " :" + e);
            }
        });
    }
}
