package com.kang.socket;

import com.kang.MyTomcat;
import com.kang.http.Request;
import com.kang.http.Response;
import com.kang.servlet.Servlet;

import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketProcess extends Thread {

    protected Socket socket;

    public SocketProcess(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Request request = new Request(socket);
            Response response = new Response(socket);


            //从映射中找
            System.out.println("url:" + request.getUrl());
            String servelName = (String) MyTomcat.servletMapping.get(request.getUrl());
            System.out.println("servelName:" + servelName);

            if (servelName != null && !servelName.isEmpty()) {

                HashMap<String, Object> servlet1 = MyTomcat.servlet;
                for (Map.Entry<String, Object> m : servlet1.entrySet()) {
                    System.out.println("key:" + m.getKey());
                }
                //映射有的话找到对应的对象
                Servlet servlet = (Servlet) MyTomcat.servlet.get(servelName);
                if (servlet != null) {
                    servlet.doGet(request, response);

                } else {

                    System.out.println("找不到对应的servlet");
                }

            } else {
                System.out.println("找不到对应的servletMapping");
            }
            String responseHeader = "HTTP/1.1 200 \r\n"
                    + "Content-Type: " + response.getContentType() + "\r\n"
                    + "\r\n";
            String res = responseHeader + response.getWrite();
            System.out.println("通过socket查看http响应报文:");
            System.out.println("**************************************************************");
            System.out.println(res);
            System.out.println("**************************************************************");
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(res.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}