package com.arrayprolc.websrv4j.listener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

import com.arrayprolc.websrv4j.response.abs.Response;

public abstract class ResponseListener {

    private static ResponseListener listener;

    public ResponseListener() {
        listener = this;
    }

    public abstract Response reply(Socket client, BufferedReader inFromClient, DataOutputStream outToClient, String query, String method);

    public static ResponseListener getListener() {
        return listener;
    }

}
