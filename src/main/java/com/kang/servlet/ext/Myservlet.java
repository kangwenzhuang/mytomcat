package com.kang.servlet.ext;

import com.kang.http.Request;
import com.kang.http.Response;
import com.kang.servlet.Servlet;

import java.util.Map;

public class Myservlet extends Servlet {

    @Override
    public void doGet(Request request, Response response) {
        System.out.println("进入了我的第一个servlet");
        Map<String, String> map=request.getMap();
        for(Map.Entry<String,String> m:map.entrySet()){
            System.out.println(m.getKey()+":"+m.getValue());
        }
        response.setContentType("text/html");
        response.setWrite("我的名字是："+map.get("name")+"\n"+"我的年龄是："+map.get("age"));
    }

    @Override
    public void doPost(Request request, Response response) {
    }
}
