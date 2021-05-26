package com.gloryroad.demo.test;

import com.google.common.base.Joiner;

import java.util.Arrays;

public class TestGloryroad {
    public static void main(String[] args) {
        Integer[] ids = {1,2,3};
        System.out.println("args = " + Joiner.on(',').join(ids));
    }
}
