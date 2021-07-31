package com.gloryroad.demo.entity.report;

import com.gloryroad.demo.constant.GloryRoadEnum;

public class ReportCase {

    private Integer id;

    private Integer caseId;

    private Integer reportBaseId;

    /** 用例名称 */
    private String caseName;

    /** 用例备注 */
    private String caseRemark;

    /** step运行状态 */
    private GloryRoadEnum.RunStatus runState = GloryRoadEnum.RunStatus.TO_BE_EXEC;

    /** 状态 */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer casesId) {
        this.caseId = casesId;
    }

    public Integer getReportBaseId() {
        return reportBaseId;
    }

    public void setReportBaseId(Integer reportBaseId) {
        this.reportBaseId = reportBaseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseRemark() {
        return caseRemark;
    }

    public void setCaseRemark(String caseRemark) {
        this.caseRemark = caseRemark;
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
}
