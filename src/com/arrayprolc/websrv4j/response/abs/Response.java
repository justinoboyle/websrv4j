package com.arrayprolc.websrv4j.response.abs;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

import com.arrayprolc.websrv4j.status.StatusCode;

public abstract class Response {

    protected StatusCode statusCode;
    protected String responseString = "";

    protected Socket client;

    protected BufferedReader inFromClient = null;
    protected DataOutputStream outToClient = null;

    public Response(StatusCode statusCode, Socket client, BufferedReader inFromClient, DataOutputStream outToClient) {
        super();
        this.statusCode = statusCode;
        this.client = client;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
    }
 
    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public abstract String getContentType();

    public abstract String getContentType(String fileName);

    public abstract boolean isFile();

    public abstract void sendResponse() throws Exception;

}
