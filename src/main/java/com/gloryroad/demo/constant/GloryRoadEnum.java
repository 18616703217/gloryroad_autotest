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

        HTTP_CODE("HTTP_CODE"),
        BODY("BODY"),
        HEADER("HEADER");

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
        IMMEDIATELY("IMMEDIATELY"),

        /**
         * 定时执行
         */
        TIMING("TIMING");
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
        PREPARE("PREPARE"),
        /**
         * 待执行
         */
        TO_BE_EXEC("TO_BE_EXEC"),
        /**
         * 执行中
         */
        EXECUTING("EXECUTING"),
        /**
         * 执行失败
         */
        EXE_FAILD("EXE_FAILD"),
        /**
         * 执行成功
         */
        EXE_SUCESS("EXE_SUCESS"),
        /**
         * 取消
         */
        CANCEL("CANCEL"),
        /**
         * 下发异常
         */
        DISTRIBUTE_ERROR("DISTRIBUTE_ERROR"),;

        private final String value;

        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        TaskStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static TaskStatus getTaskStatus(String value) {
            for (TaskStatus taskStatus : TaskStatus.values()){
                if(StringUtil.isNotBlank(value) && value.equals(taskStatus.getValue())){
                    return taskStatus;
                }
            }
            return null;
        }

    }

    /**
     * 用例/step执行的状态
     */
    public static enum RunStatus {
        /**
         * 准备中
         */
        TO_BE_EXEC("TO_BE_EXEC"),
        /**
         * 执行中
         */
        EXECUTING("EXECUTING"),
        /**
         * 失败
         */
        EXE_FAILD("EXE_FAILD"),
        /**
         * 成功
         */
        EXE_SUCESS("EXE_SUCESS"),
        /**
         * 被阻断
         */
        BE_BLOCKED("BE_BLOCKED"),;

        private final String value;
        //构造器默认也只能是private, 从而保证构造函数只能在内部使用
        RunStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static RunStatus getRunStatus(String value) {
            for (RunStatus runStatus : RunStatus.values()){
                if(StringUtil.isNotBlank(value) && value.equals(runStatus.getValue())){
                    return runStatus;
                }
            }
            return null;
        }

    }

    /**
     * 任务执行频率
     */
    public static enum RunMode {
        /**
         * 准备中
         */
        PERIOD_ONCE("PERIOD_ONCE"),
        PERIOD_SECOND("PERIOD_SECOND"),
        PERIOD_MINUTE("PERIOD_MINUTE"),
        PERIOD_HOUR("PERIOD_HOUR"),
        PERIOD_DAY("PERIOD_DAY"),
        PERIOD_WEEK("PERIOD_WEEK"),
        PERIOD_MONTH("PERIOD_MONTH");

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
