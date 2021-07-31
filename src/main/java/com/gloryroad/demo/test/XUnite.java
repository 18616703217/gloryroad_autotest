package com.gloryroad.demo.test;


import java.lang.reflect.InvocationTargetException;

public class XUnite extends XUniteInterfac {
    @Override
    public void setUp() {

    }

    @Override
    public void tearDown() {

    }

    public static void test1(){
        System.out.println(assertString("1", "1"));
    }

    public static void test2(){
        System.out.println(assertString("1", "1"));
    }

    public static void test3(){
        System.out.println(assertString("1", "1"));
    }

    public static void main(String[] args) {
        XUnite x = new XUnite();
        x.run();
    }
}
