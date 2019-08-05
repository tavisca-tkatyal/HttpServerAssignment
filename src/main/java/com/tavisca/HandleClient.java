package com.tavisca;

import java.io.*;
import java.net.Socket;

public class HandleClient {
    static private String RESOURCE_PATH ;
    static private String resourceToSend = "";
    HandleClient(String RESOURCE_PATH)
    {
        this.RESOURCE_PATH = RESOURCE_PATH;
    }
    public static void handleClient(Socket client)
    {
        int resourceDataByte= -1;
        String metaData =  "Server: My Java HTTP Server : 1.0\n"+"Content-type: text/html\n";
        try {
            InputStreamReader streamComingIntoServer = new InputStreamReader(client.getInputStream());
            BufferedOutputStream serverToClientData = new BufferedOutputStream(client.getOutputStream());
            char[] requestedDataReader=new char[256];
            streamComingIntoServer.read(requestedDataReader);
            System.out.println(requestedDataReader);
            String requestedInformation = String.valueOf(requestedDataReader);
            String[] requestedInformationInArray = requestedInformation.split(" ");
            if (requestedInformationInArray[0].equalsIgnoreCase("GET"))
            {
                resourceToSend = RESOURCE_PATH + "\\" + requestedInformationInArray[1].substring(1);
                BufferedInputStream resourceReader = null;
                try
                {
                    resourceReader = new BufferedInputStream(new FileInputStream(resourceToSend));
                    metaData = "HTTP/1.1 200 OK" + metaData;
                    System.out.println("FILE FOUND ");
                }
                catch (FileNotFoundException f)
                {
                    System.out.println("FILE NOT FOUND");
                    metaData = "HTTP/1.1 404 NOT FOUND"+metaData;
                    resourceToSend = RESOURCE_PATH + "\\" + "404Error.html";
                    resourceReader = new BufferedInputStream(new FileInputStream(resourceToSend));
                }
                metaData +=resourceReader.available()+"\n\n";
                serverToClientData.write(metaData.getBytes());
                while ((resourceDataByte = resourceReader.read()) != -1)
                {
                    serverToClientData.write((byte) resourceDataByte);
                }
                serverToClientData.flush();
                resourceReader.close();
                streamComingIntoServer.close();
                serverToClientData.close();
                System.out.println("--------------------------------------------");
                System.out.println("--------------------------------------------");
            }
        }catch (Exception e)
        {
            System.out.println("There has been an error "+e.getMessage());
        }
    }
}
