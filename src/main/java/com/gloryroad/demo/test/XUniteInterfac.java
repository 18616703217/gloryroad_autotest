package com.gloryroad.demo.test;

import org.assertj.core.util.Lists;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class XUniteInterfac implements XUniteBase{

    public static int excuteSuccessTimes = 0;
    public static int excuteFaildTimes = 0;

    public static List<String> excuteSuccessDatas = Lists.newArrayList();
    public static List<String> excuteFaildDatas = Lists.newArrayList();


    public abstract void setUp();

    public abstract void tearDown();

    public void run(){
        Class<?> clz = this.getClass();
        Method[] methods =clz.getMethods();
        for (Method method : methods) {
            int cacheS = excuteSuccessTimes;
            int cacheF = excuteFaildTimes;
            if(method.getName().startsWith("test")) {
                try {
                    method.invoke(clz);
                    if(excuteSuccessTimes > cacheS){
                        excuteSuccessDatas.add(method.getName());
                    }

                    if(excuteFaildTimes > cacheF){
                        excuteFaildDatas.add(method.getName());
                    }
                }catch (Exception e){
                    return;
                }

            }
        }
        for(String funcName: excuteSuccessDatas){
            System.out.printf("用例 %s 执行成功\n", funcName);
        }

        for(String funcName: excuteFaildDatas){
            System.out.printf("用例 %s 执行成功\n", funcName);
        }
        System.out.printf("执行成功用例 %s 个， 执行失败用例 %s 个", excuteSuccessTimes, excuteFaildTimes);
    }

    public static Boolean assertString(String a, String b){
        if (a == null){
            excuteFaildTimes += 1;
            return false;
        }
        excuteSuccessTimes += 1;
        return a.equals(b);
    }
}
