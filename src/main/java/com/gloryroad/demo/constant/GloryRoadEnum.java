package com.gloryroad.demo.constant;

import com.gloryroad.demo.utils.StringUtil;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class GloryRoadEnum {
    public static void main(String[] args) {
        System.out.println(Role.TEST.getRoleName());
    }
    /** 角色*/
    public enum Role {
        TEST("测试"){
            @Override
            public String getRoleName(){
                return "测试";
            }
        },
        PRODUCT("产品"){
            @Override
            public String getRoleName(){
                return "产品";
            }
        },
        DEVELOPMENT("开发"){
            @Override
            public String getRoleName() {
                return "开发";
            }
        };

        public static List<String> getNames(){
            List<String> list = Lists.newArrayList(
                    TEST.name,
                    PRODUCT.name,
                    DEVELOPMENT.name);
            return list;
        }

        /** 输出角色*/
        private String name;

        Role(String roleName) {
            this.name = roleName;
        }

        public abstract String getRoleName();

    }

    /**
     * 用例提交方法（get，post）
     */
    public static enum CaseSubMethod {

        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE"),
        COPY("COPY"),
        HEAD("HEAD"),
        OPTIONS("OPTIONS"),
        LINK("LINK"),
        UNLINK("UNLINK"),
        PURGE("PURGE"),
        LOCK("LOCK"),
        UNLOCK("UNLOCK"),
        PROPFIND("PROPFIND"),
        VIEW("VIEW"),
        PATCH("PATCH");

        private final String value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        CaseSubMethod(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static CaseSubMethod getCaseSubMethod(String value) {
            for (CaseSubMethod caseSubMethod : CaseSubMethod.values()){
                if(StringUtil.isNotBlank(value) && value.equals(caseSubMethod.getValue())){
                    return caseSubMethod;
                }
            }
            return null;
        }

    }
}
