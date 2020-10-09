package com.lee.tank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.util.Properties;

public class PropertyManager {
    private static  final Logger logger= LoggerFactory.getLogger(PropertyManager.class);
    static  Properties properties=new Properties();

    static{
        try{
            properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("config.properties"));  //加载配置文件
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Object get(String key){

        if (key.endsWith("_int")){
                return Integer.parseInt((String) properties.get(key));
        }
        if(key.endsWith("_String")) {
            return (String) properties.get(key);
        }
        return null;
    }







}
