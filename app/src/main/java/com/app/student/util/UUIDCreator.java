package com.app.student.util;

import java.util.Random;
import java.util.UUID;

/***
 * 生成唯一ID
 */
public class UUIDCreator {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * java生成随机数字10位数
     *    
     */
    public static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }


}
