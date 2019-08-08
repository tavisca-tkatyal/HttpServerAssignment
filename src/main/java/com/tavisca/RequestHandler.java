package com.tavisca;

import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {
    private ResponseHandler responseHandler = new ResponseHandler();
    final String requestPattern="(.*)\\s\\/(.*)(HTTP\\/1\\.1)";

    public void requestHandling(String request, Socket client) throws IOException {

        String fileName = parseRequest(request);
        //System.out.println(fileName);
        responseHandler.serveClient(fileName,client);

    }
    private String parseRequest(String request)
    {
        String filename="";
        Pattern pattern = Pattern.compile(requestPattern);
        Matcher matcher = pattern.matcher(request);
        if(matcher.find())
            filename = matcher.group(2);
        return filename;
    }
}