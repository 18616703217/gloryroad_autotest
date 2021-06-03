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

        public static Role getRole(String value) {
            for (Role role : Role.values()){
                if(StringUtil.isNotBlank(value) && value.equals(role.getRoleName())){
                    return role;
                }
            }
            return null;
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

    /**
     * http请求主体类型（json, form）
     */
    public static enum CaseBodyType {

        JSON("JSON"),
        FORM("FORM");


        private final String value;

        CaseBodyType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static CaseBodyType getCaseBodyType(String value) {
            for (CaseBodyType caseBodyType : CaseBodyType.values()){
                if(StringUtil.isNotBlank(value) && value.equals(caseBodyType.getValue())){
                    return caseBodyType;
                }
            }
            return null;
        }

    }

    /**
     * 断言内容
     */
    public static enum AssertPosition {

        HTTP_CODE("httpCode"),
        BODY("body"),
        HEADER("header");

        private final String value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        AssertPosition(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static AssertPosition getAssertPosition(String value) {
            for (AssertPosition assertPosition : AssertPosition.values()){
                if(StringUtil.isNotBlank(value) && value.equals(assertPosition.getValue())){
                    return assertPosition;
                }
            }
            return null;
        }

    }

    /**
     * 任务类型（立即执行/定时执行）
     */
    public static enum ExecutionType {

        /**
         * 立即执行
         */
        IMMEDIATELY("立即执行"),

        /**
         * 定时执行
         */
        TIMING("定时执行");
        private final String value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        ExecutionType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * 任务执行的状态
     */
    public static enum TaskStatus {

        /**
         * 准备中
         */
        PREPARE("准备中"),
        TO_BE_EXEC("待执行"),
        EXECUTING("执行中"),
        EXE_FAILD("执行失败"),
        EXE_SUCESS("执行成功"),
        CANCEL("取消"),
        DISTRIBUTE_ERROR("下发异常"),;

        private final String value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        TaskStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * 用例/step执行的状态
     */
    public static enum RunStatus {
        /**
         * 准备中
         */
        TO_BE_EXEC("待执行"),
        EXECUTING("执行中"),
        EXE_FAILD("执行失败"),
        EXE_SUCESS("执行成功"),
        BE_BLOCKED("被阻断"),;

        private final String value;
        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        RunStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * 任务执行频率
     */
    public static enum RunMode {
        /**
         * 准备中
         */
        PERIOD_ONCE("一次"),
        PERIOD_SECOND("秒频率"),
        PERIOD_MINUTE("分钟频率"),
        PERIOD_HOUR("小时频率"),
        PERIOD_DAY("天频率"),
        PERIOD_WEEK("周频率"),
        PERIOD_MONTH("月频率");

        private final String value;
        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        RunMode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }
}
