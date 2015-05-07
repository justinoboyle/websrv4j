package com.arrayprolc.websrv4j.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;

import com.arrayprolc.websrv4j.listener.LogListener;
import com.arrayprolc.websrv4j.listener.ResponseListener;

public class WebServer {

    ServerSocket Server = null;
    private WebServerThread ws = null;

    private LogListener logger;
    private ResponseListener listener;

    private int port;

    public String ip = "";

    public WebServer(LogListener logger, ResponseListener listener, int port) {
        super();
        this.logger = logger;
        this.listener = listener;
        this.port = port; 
    }

    private boolean checkServer(String ip) {
        try {
            URL u = new URL("http://" + ip + ":" + getPort() + "/ping");
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");
            huc.connect();
            return (huc.getResponseCode() == 222);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void disable() {
        try {
            this.getWs().interrupt();
            this.getWs().running = false;
            this.setWs(null);
            this.Server.close();
        } catch (IOException localIOException) {
        }
        logger.log("Server disabled.");
    }

    protected String getExternalIP() {
        String i = "";
        try {
            URL whatismyip = new URL("http://automation.whatismyip.com/n09230945.asp");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            i = in.readLine();
        } catch (Exception e) {
            return "";
        }
        return i;
    }

    public void enable() {
        logger.log("Starting server");
        (this.setWs(new WebServerThread(this))).start();
        try {
            String i = getExternalIP();
            if ((i != "") && (checkServer(i))) {
                logger.log("Got IP: " + InetAddress.getLocalHost().getHostAddress());
                logger.log("Warning: It is recommended that you port forward the port defined as only users on your local network can access the web server!");
                this.ip = InetAddress.getLocalHost().getHostAddress();
                return;
            }
        } catch (UnknownHostException localUnknownHostException) {
            try {
                if (checkServer(InetAddress.getLocalHost().getHostAddress())) {
                    logger.log("Got IP: " + InetAddress.getLocalHost().getHostAddress());
                    logger.log("Warning: It is recommended that you port forward the port defined as only users on your Local Networkk can access the web server!");
                    this.ip = InetAddress.getLocalHost().getHostAddress();
                    return;
                }
            } catch (UnknownHostException localUnknownHostException1) {
                if (checkServer("localhost")) {
                    logger.log("Got IP: localhost");
                    logger.log("Warning: It is recommended that you port forward the port defined as other users cannot access the web server!");
                    this.ip = "localhost";
                    return;
                }
            }
        }
    }

    public WebServerThread getWs() {
        return ws;
    }

    public WebServerThread setWs(WebServerThread ws) {
        this.ws = ws;
        return ws;
    }

    public LogListener getLogger() {
        return logger;
    }

    public void setLogger(LogListener logger) {
        this.logger = logger;
    }

    public ResponseListener getListener() {
        return listener;
    }

    public void setListener(ResponseListener listener) {
        this.listener = listener;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerSocket getServerSocket() {
        return Server;
    }

    public String getIP() {
        return ip;
    }

}
