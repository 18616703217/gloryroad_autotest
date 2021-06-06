package com.gloryroad.demo.dto.report;

import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.entity.cases.CasesBasic;
import com.gloryroad.demo.entity.report.ReportBasic;

import java.util.List;

public class ReportBasicDto extends ReportBasic {
    private String groupName;

    private String createUserName;

    private List<ReportCaseDto> reportCaseDtos;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public List<ReportCaseDto> getReportCaseDtos() {
        return reportCaseDtos;
    }

    public void setReportCaseDtos(List<ReportCaseDto> reportCaseDtos) {
        this.reportCaseDtos = reportCaseDtos;
    }
}
