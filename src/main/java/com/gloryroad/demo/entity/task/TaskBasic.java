package com.gloryroad.demo.entity.task;

import com.gloryroad.demo.constant.GloryRoadEnum;

import java.util.List;

public class TaskBasic {

    /** 任务id */
    private Integer id;

    /** 任务名称 */
    private String taskName;

    /** 任务描述 */
    private String remark;

    /** 执行用例集合 */
    private List<Integer> casesIds;

    /** 创建账户 */
    private String createAccount;

    /** 关联业务分组id */
    private Integer groupId;

    /** 创建时间 */
    private long createTime;

    /** 状态 */
    private Integer status;

    public List<Integer> getCasesIds() {
        return casesIds;
    }

    public void setCasesIds(List<Integer> casesIds) {
        this.casesIds = casesIds;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
