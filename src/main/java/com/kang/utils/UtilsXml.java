package com.kang.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

public class UtilsXml {
    //定义解析器和文档对象
    public SAXReader saxReader;
    public Document document;

    public UtilsXml(String path) {
        //获取解析器
        saxReader = new SAXReader();
        try {
            //获取文档对象
            document = saxReader.read(path);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 根据节点名称获取内容
     *
     * @param name 节点名称
     * @return 节点内容
     */
    public String getElementText(String name) {
        //定位根节点
        Element root = document.getRootElement();

        List<Element> mapp = root.elements("servlet-mapping");

        List<Element> servlet = root.elements("servlet");

        String serveltName = "";
        String classpath = "";

        for (Element e : mapp) {
//        	System.out.println(e.element("url-pattern").getText());
            if (e.element("url-pattern").getText().equals(name)) {
                serveltName = e.element("servlet-name").getText();
                break;
            }
        }


        for (Element e : servlet) {
//        	System.out.println(e.element("servlet-name").getText());
            if (e.element("servlet-name").getText().equals(serveltName)) {
                classpath = e.element("servlet-class").getText();
                break;
            }
        }

        return classpath;
//        //根据名称定位节点
//        Element element = root.element(name);
//        //返回节点内容
//        return element.getText();
    }

    /**
     * 获取节点下的所有节点
     *
     * @param root
     * @param name
     * @return
     */
    public List<Element> getNodes(String name) {
        Element root = document.getRootElement();
        return root.elements(name);
    }

    public static void main(String[] args) {
        UtilsXml xml = new UtilsXml(UtilsXml.class.getResource("/") + "web.xml");
        //System.out.println(xml.getElementText("/myhtml.html"));

        List<Element> list = xml.getNodes("servlet");
        for (Element element : list) {
            System.out.println(element.element("servlet-name").getText());
            System.out.println(element.element("servlet-class").getText());
        }

    }
}