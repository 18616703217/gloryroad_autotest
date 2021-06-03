package com.gloryroad.demo.test;

import com.gloryroad.demo.utils.engine.OKHttpUtil;

import java.io.File;

public class TestOKHttp {
    public static void main(String[] args) {
        OKHttpUtil okHttpUtil = OKHttpUtil.getInstance();
        File f=new File("./test.txt");
        int code = okHttpUtil.doGetcode("http://www.baidu.com", f);
        System.out.println("t = " + code);
    }
}
