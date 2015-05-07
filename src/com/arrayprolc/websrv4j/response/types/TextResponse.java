package com.arrayprolc.websrv4j.response.types;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

import com.arrayprolc.websrv4j.response.abs.Response;
import com.arrayprolc.websrv4j.status.StatusCode;

public class TextResponse extends Response {

    public TextResponse(StatusCode statusCode, Socket client, BufferedReader inFromClient, DataOutputStream outToClient) {
        super(statusCode, client, inFromClient, outToClient);
    }

    @Override
    public String getContentType() {
        return "Content-Type: text/html\r\n";
    }

    @Override
    public String getContentType(String fileName) {
        return getContentType();
    }

    @Override
    public boolean isFile() { 
        return false;
    }

    @Override
    public void sendResponse() throws Exception {
        String statusLine = statusCode.getResponseText();
        String serverdetails = "Server: Java HTTPServer";
        String contentLengthLine = null;
        String contentTypeLine = getContentType();
        statusLine = statusCode.getResponseText();

        contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";

        if (!this.client.isClosed()) {
            this.outToClient.writeBytes(statusLine);
            this.outToClient.writeBytes(serverdetails + "\r\n");
            this.outToClient.writeBytes(contentTypeLine);
            this.outToClient.writeBytes(contentLengthLine);
            this.outToClient.writeBytes("Connection: close\r\n");
            this.outToClient.writeBytes("\r\n");

            this.outToClient.writeBytes(responseString);

            this.outToClient.close();
        }
    }

}
