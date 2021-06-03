package com.gloryroad.demo.entity.engine;

import com.gloryroad.demo.constant.GloryRoadEnum;

import java.util.List;

public class EngineActionBasic {

    /** 执行用例集合 */
    private List<Integer> casesIds;

    /** 创建账户 */
    private String createAccount;

    /** 创建人邮件 */
    private String mail;

    /** 关联业务分组id */
    private Integer groupId;

    /** 创建时间 */
    private long createTime;

    /** 执行模式 */
    private GloryRoadEnum.RunMode runMode;

    /** 执行开始时间 */
    private long startTime;

    /** 执行模式具体内容 */
    private Integer runModeContent;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public GloryRoadEnum.RunMode getRunMode() {
        return runMode;
    }

    public void setRunMode(GloryRoadEnum.RunMode runMode) {
        this.runMode = runMode;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Integer getRunModeContent() {
        return runModeContent;
    }

    public void setRunModeContent(Integer runModeContent) {
        this.runModeContent = runModeContent;
    }
}
