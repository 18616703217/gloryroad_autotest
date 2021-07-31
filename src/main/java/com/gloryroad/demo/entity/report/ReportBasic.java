package com.gloryroad.demo.entity.report;

import com.gloryroad.demo.constant.GloryRoadEnum;

public class ReportBasic {

    private Integer id;

    private String taskId;

    /** 关联业务分组id */
    private Integer groupId;

    /** 创建账号 */
    private String createAccount;

    private String reportName;

    private String executionEnv = "";

    private long startTime;

    private long endTime;

    /** 任务执行状态，写在报告里面 */
    private GloryRoadEnum.TaskStatus taskStatus;

    /** 失败原因 */
    private String errMsg = "";

    private long createTime;

    /** 状态 */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskNo) {
        this.taskId = taskNo;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getExecutionEnv() {
        return executionEnv;
    }

    public void setExecutionEnv(String executionEnv) {
        this.executionEnv = executionEnv;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public GloryRoadEnum.TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(GloryRoadEnum.TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
