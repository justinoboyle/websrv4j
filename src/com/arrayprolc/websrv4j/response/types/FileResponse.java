package com.arrayprolc.websrv4j.response.types;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

import com.arrayprolc.websrv4j.response.abs.Response;
import com.arrayprolc.websrv4j.status.StatusCode;

public class FileResponse extends Response {

    public FileResponse(StatusCode statusCode, Socket client, BufferedReader inFromClient, DataOutputStream outToClient) {
        super(statusCode, client, inFromClient, outToClient);
    }

    @Override
    public String getContentType() {
        return "Content-Type: text/html\r\n";
    }

    @Override
    public String getContentType(String fileName) {
        if ((!fileName.endsWith(".htm")) && (!fileName.endsWith(".html"))) {
            return "Content-Type: application/zip\r\n";
        } 
        return getContentType();
    }

    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public void sendResponse() throws Exception {
        String statusLine = statusCode.getResponseText();
        String serverdetails = "Server: Java HTTPServer";
        String contentLengthLine = null;
        String fileName = null;
        String contentTypeLine = getContentType();
        FileInputStream fin = null;
        statusLine = statusCode.getResponseText();

        fileName = responseString;
        fin = new FileInputStream(fileName);
        contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";

        contentTypeLine = getContentType();

        if (!this.client.isClosed()) {
            this.outToClient.writeBytes(statusLine);
            this.outToClient.writeBytes(serverdetails + "\r\n");
            this.outToClient.writeBytes(contentTypeLine);
            this.outToClient.writeBytes(contentLengthLine);
            this.outToClient.writeBytes("Connection: close\r\n");
            this.outToClient.writeBytes("\r\n");

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fin.read(buffer)) != -1) {
                this.outToClient.write(buffer, 0, bytesRead);
            }
            fin.close();

            this.outToClient.close();
        }
    }

}
