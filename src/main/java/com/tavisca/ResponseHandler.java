package com.tavisca;

import java.io.*;
import java.net.Socket;


public class ResponseHandler {

    private String resourceToSend = "";

    BufferedOutputStream bufferedOutputStream;

    public void serveClient(String fileName, Socket client) throws IOException {
        String statusCode ="200 Ok";
        bufferedOutputStream = new BufferedOutputStream(client.getOutputStream());
        String resourceToSend = fetchFile(fileName);
        writeMetaData(bufferedOutputStream,fileName,statusCode);
        writeFileToClient(bufferedOutputStream,resourceToSend,statusCode);
        bufferedOutputStream.close();
        client.close();
    }
    private void writeMetaData(BufferedOutputStream bufferedOutputStream,String fileName,String statusCode) throws IOException {
        String versionAndStatusHeader="HTTP/1.1"+statusCode+"\r\n";
        String metaData =  "Server: My Java HTTP Server : 1.0";
        String contentType = "Content-type: "+ getContentType(fileName)+"\r\n";
        String fileSize = "File Size is: "+ getFileSize(fileName);
        String closeConnection = "Connection: close\r\n\r\n";
        bufferedOutputStream.write(versionAndStatusHeader.getBytes());
        bufferedOutputStream.write(metaData.getBytes());
        bufferedOutputStream.write(contentType.getBytes());
        bufferedOutputStream.write(fileSize.getBytes());
        bufferedOutputStream.write(closeConnection.getBytes());
    }

    private void writeFileToClient(BufferedOutputStream bufferedOutputStream,String resourceToSend,String statusCode) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(resourceToSend);
        byte[] buffer=new byte[fileInputStream.available()];
        fileInputStream.read(buffer);
        bufferedOutputStream.write(buffer);
    }

    private String fetchFile(String fileName)
    {
        String RESOURCE_PATH = "src/main/resources/";
     //  resourceToSend = RESOURCE_PATH + fileName;
       resourceToSend="C:\\Users\\tkatyal\\Desktop\\TrainingWork\\HowWebWorks\\HttpAssignment\\src\\main\\resources\\Welcome.html";
        return resourceToSend;
    }

    private String getContentType(String fileName) {
        try {
            String[] splits = fileName.split("[.]");
            String extensionOfFile = splits[1];
            return extensionOfFile;
        }
        catch(ArrayIndexOutOfBoundsException ae){
            return "html";
        }
    }

    private String getFileSize(String fileName) {
        File file = new File(fileName);
        double size = file.length();
        String fileSize = Double.toString(size);
        return fileSize;
    }

}
