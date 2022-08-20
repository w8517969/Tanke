package com.mashibing.tank.resourceMgr;

import java.io.IOException;
import java.util.Properties;

/**
 * @Auther: szbb-544826634
 * @Date: 2022/8/12 - 08 - 12 - 14:15
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class PropertyMgr {
    static Properties properties=new Properties();
    static {
        try {
            properties.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String get(String key){
        if (properties==null){return null;}
        return (String) properties.get(key);
    }
}
