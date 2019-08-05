package com.tavisca;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.tavisca.HandleClient.handleClient;


public class Server
{
    static private String RESOURCE_PATH = "C:\\Users\\tkatyal\\Desktop\\HttpAssignment\\src\\main\\resources";

    HandleClient clientHandle = new HandleClient(RESOURCE_PATH);
    public static void main(String[] args)
    {
        try
        {
            ServerSocket server = new ServerSocket(8080);
            while(true)
            {
                Socket client = server.accept();
                new Thread(()->handleClient(client)).start();
            }
        }
        catch(IOException e)
        {
            System.out.println("There is an Error in URL "+e.getMessage());
        }
    }

}