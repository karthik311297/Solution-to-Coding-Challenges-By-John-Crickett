package com.cchallenges.memcached;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MemcachedServer
{
    public static void main(String[] args) throws IOException
    {
        int port = 9999;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Memcached Server listening on port: " + port);
        while(true)
        {
            Socket socket = server.accept();
            SocketConnectionManager.handleClientConnection(socket);
        }
    }
}
