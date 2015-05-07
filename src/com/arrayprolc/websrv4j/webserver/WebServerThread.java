package com.arrayprolc.websrv4j.webserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class WebServerThread extends Thread {
    
    public WebServer webServer = null;
    public boolean running = true;

    public WebServerThread(WebServer webServer) {
        this.webServer = webServer;
    }

    public void run() {
        try {
            this.webServer.Server = new ServerSocket(webServer.getPort(), 10, InetAddress.getByName("0"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        while (running) { 
            try {
                new WebHandlerThread(this.webServer.Server.accept(), this).start();
            } catch (IOException ex) {
               webServer.getLogger().log(ex.getLocalizedMessage());
            }
        }
    }
}
