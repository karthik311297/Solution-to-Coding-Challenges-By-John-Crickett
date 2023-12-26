package com.cchallenge.nats;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NATSBroker
{
    public static void main(String[] args) throws IOException
    {
        int port = 4222;
        ServerSocket server = new ServerSocket(port);
        System.out.println("NATS Broker listening on port: " + port);
        while(true)
        {
            Socket socket = server.accept();
            SocketConnectionManager.handleClientConnection(socket);
        }
    }
}
