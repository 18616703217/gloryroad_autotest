package com.gloryroad.demo.entity.cases;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.constant.GloryRoadEnum;

public class CasesInterfac {

    /** 主键 */
    private Integer id;
    /** 用例id */
    private Integer casesId;
    /** 执行步骤 */
    private Integer stepNum;
    /** 接口名称 */
    private String interfacName;
    /** 接口说明 */
    private String remark = "";
    /** 创建账户 */
    private String createAccount;
    /** url */
    private String url;

    /** 请求方法 */
    private GloryRoadEnum.CaseSubMethod methodType;
    /** 请求主体 */
    private GloryRoadEnum.CaseBodyType bodyType;
    /** 请求header数据 */
    private JSONObject interfacHeaderData = new JSONObject();
    /** 请求form数据 */
    private JSONObject interfacFormData = new JSONObject();
    /** 请求query数据 */
    private JSONObject interfacQueryData = new JSONObject();
    /** 请求json数据 */
    private JSONObject interfacJsonData = new JSONObject();

    /** 状态 */
    private Integer status;
    /** 创建时间 */
    private long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInterfacName() {
        return interfacName;
    }

    public void setInterfacName(String interfacName) {
        this.interfacName = interfacName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public GloryRoadEnum.CaseSubMethod getMethodType() {
        return methodType;
    }

    public void setMethodType(GloryRoadEnum.CaseSubMethod methodType) {
        this.methodType = methodType;
    }

    public JSONObject getInterfacHeaderData() {
        return interfacHeaderData;
    }

    public void setInterfacHeaderData(JSONObject interfacHeaderData) {
        this.interfacHeaderData = interfacHeaderData;
    }

    public JSONObject getInterfacFormData() {
        return interfacFormData;
    }

    public void setInterfacFormData(JSONObject interfacFormData) {
        this.interfacFormData = interfacFormData;
    }

    public JSONObject getInterfacQueryData() {
        return interfacQueryData;
    }

    public void setInterfacQueryData(JSONObject interfacQueryData) {
        this.interfacQueryData = interfacQueryData;
    }

    public JSONObject getInterfacJsonData() {
        return interfacJsonData;
    }

    public void setInterfacJsonData(JSONObject interfacJsonData) {
        this.interfacJsonData = interfacJsonData;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    public GloryRoadEnum.CaseBodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(GloryRoadEnum.CaseBodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Integer getCasesId() {
        return casesId;
    }

    public void setCasesId(Integer casesId) {
        this.casesId = casesId;
    }

    public Integer getStepNum() {
        return stepNum;
    }

    public void setStepNum(Integer stepNum) {
        this.stepNum = stepNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
