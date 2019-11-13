package com.kang;

import com.kang.socket.SocketProcess;
import com.kang.utils.UtilsXml;
import org.dom4j.Element;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class MyTomcat {
    private static final int port;

    private static Properties prop;

    public static final HashMap<String, Object> servletMapping = new HashMap<String, Object>();

    public static final HashMap<String, Object> servlet = new HashMap<String, Object>();

    static {
        prop = new Properties();
        try {
            File file = new File(MyTomcat.class.getClassLoader().getResource("property.properties").getFile());
            prop.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        port = Integer.parseInt(prop.getProperty("port"));
    }

    private void init() {

        InputStream io = null;

        String basePath;

        try {
            System.out.println("加载配置文件开始");
            //读取web.xml
            UtilsXml xml = new UtilsXml(MyTomcat.class.getClassLoader().getResource("web.xml").getFile());
            //讲所有的类都存储到容器中 并且创造对象
            List<Element> list = xml.getNodes("servlet");
            for (Element element : list) {
                servlet.put(element.element("servlet-name").getText(), Class.forName(element.element("servlet-class").getText()).newInstance());
            }
            //映射关系创建
            List<Element> list2 = xml.getNodes("servlet-mapping");
            for (Element element : list2) {
                servletMapping.put(element.element("url-pattern").getText(), element.element("servlet-name").getText());
            }
            System.out.println("加载配置文件结束");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (io != null) {
                try {
                    io.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Tomcat 服务已启动，地址：localhost ,端口：" + port);
            this.init();
            //持续监听
            do {
                Socket socket = serverSocket.accept();
                //处理任务
                Thread thread = new SocketProcess(socket);

                thread.start();
            } while (true);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyTomcat tomcat = new MyTomcat();
        tomcat.start();

    }

}