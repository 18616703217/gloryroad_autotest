package com.gloryroad.demo.entity.report;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;

import java.util.List;
import java.util.Map;

public class ReportInterfac {

    private Integer id;

    private Integer reportCaseId;

    /** 接口ID */
    private Integer casesInterfacId;

    /** 接口名称 */
    private String interfacName;

    /** 执行步骤 */
    private Integer stepNum;
    /**
     * 真正运行的url
     */
    private String url;

    /**
     * step 备注
     */
    private String remark;

    /**
     * 提交方式（get/post）
     */
    private GloryRoadEnum.CaseSubMethod methodType;

    private String queryDatas;
    /**
     * form（提交时的真实参数）
     */
    private String forms;

    /**
     * header（提交时的真实参数）
     */
    private String headers;

    /**
     * json（提交时的真实参数）
     */
    private String jsons;

    /**
     * json（提交时的真实参数）
     */
    private String response;

    /**
     * 断言内容
     */
    private String asserts = "";

    /** 状态 */
    private Integer status;

    /** step运行状态 */
    private GloryRoadEnum.RunStatus runState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportCaseId() {
        return reportCaseId;
    }

    public void setReportCaseId(Integer reportCaseId) {
        this.reportCaseId = reportCaseId;
    }

    public Integer getCasesInterfacId() {
        return casesInterfacId;
    }

    public void setCasesInterfacId(Integer casesInterfacId) {
        this.casesInterfacId = casesInterfacId;
    }

    public String getInterfacName() {
        return interfacName;
    }

    public void setInterfacName(String interfacName) {
        this.interfacName = interfacName;
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

    public String getQueryDatas() {
        return queryDatas;
    }

    public void setQueryDatas(String queryDatas) {
        this.queryDatas = queryDatas;
    }

    public String getForms() {
        return forms;
    }

    public void setForms(String forms) {
        this.forms = forms;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getJsons() {
        return jsons;
    }

    public void setJsons(String jsons) {
        this.jsons = jsons;
    }

    public GloryRoadEnum.RunStatus getRunState() {
        return runState;
    }

    public void setRunState(GloryRoadEnum.RunStatus runState) {
        this.runState = runState;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAsserts() {
        return asserts;
    }

    public void setAsserts(String asserts) {
        this.asserts = asserts;
    }
}

