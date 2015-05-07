package com.arrayprolc.websrv4j.webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.StringTokenizer;

import com.arrayprolc.websrv4j.listener.ResponseListener;
import com.arrayprolc.websrv4j.status.StatusCode;

public class WebHandlerThread extends Thread {
    private Socket client;
    private BufferedReader inFromClient = null;
    private DataOutputStream outToClient = null;
    private WebServerThread ws = null;

    public WebHandlerThread(Socket c, WebServerThread w) {
        this.client = c;
        this.ws = w;
    }

    public void run() {
        try {
            handleResponse();
        } catch (Exception localException) {
            
        }
    }

    public void handleResponse() throws Exception {
        this.inFromClient = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        this.outToClient = new DataOutputStream(this.client.getOutputStream());
        String requestString = this.inFromClient.readLine();
        String headerLine = requestString;
        StringTokenizer tokenizer = new StringTokenizer(headerLine);
        String httpMethod = tokenizer.nextToken();
        String httpQueryString = tokenizer.nextToken();
        while (this.inFromClient.ready()) {
            requestString = this.inFromClient.readLine();
        }
        httpQueryString = URLDecoder.decode(httpQueryString, "UTF-8");
        ws.webServer.getListener().reply(client, inFromClient, outToClient, httpQueryString, httpMethod);
        return;
    }
    

}