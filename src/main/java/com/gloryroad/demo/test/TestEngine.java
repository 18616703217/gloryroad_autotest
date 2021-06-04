package com.gloryroad.demo.test;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.entity.engine.EngineActionBasic;

import java.util.Arrays;

public class TestEngine {

    public static void main(String[] args) {
        EngineActionBasic engineActionBasic = new EngineActionBasic();
        engineActionBasic.setGroupId(1);
        engineActionBasic.setCreateAccount("ztb");
        System.out.println("engineActionBasic = " + JSONObject.toJSONString(engineActionBasic).toString());
        System.out.println(JSONObject.toJSONString(null));
    }
}
