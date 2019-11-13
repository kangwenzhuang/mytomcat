package com.kang.http;

import java.io.*;
import java.net.Socket;

public class Response {
    private Socket client;
    private PrintStream ps;
    private String write;
    private String contentType;



    public PrintStream getPs() {
        return ps;
    }

    public void setPs(PrintStream ps) {
        this.ps = ps;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public Response(Socket client) {
        this.client = client;
        try {
            ps = new PrintStream(client.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}