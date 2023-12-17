package com.cchallenge.loadbalancer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HealthChecker
{
    public enum STATUS
    {
        UP,
        DOWN;
    }
    
    public static STATUS getHealthStatusOfServer(BackendServer backendServer)
    {
        try
        {
            Socket socket = new Socket(backendServer.getAddress(), backendServer.getPort());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(" GET /health HTTP/1.1\r");
            bufferedWriter.flush();
            System.out.println(backendServer.toString() + "---" + bufferedReader.readLine());
            bufferedWriter.close();
            bufferedReader.close();
            socket.close();
            return STATUS.UP;
        }
        catch(Exception e)
        {
            System.out.println(HealthChecker.class.getSimpleName() + " :" + e);
            return STATUS.DOWN;
        }
    }
}
