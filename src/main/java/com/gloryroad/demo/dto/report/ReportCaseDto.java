package com.gloryroad.demo.dto.report;

import com.gloryroad.demo.entity.cases.CasesInterfac;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;
import com.gloryroad.demo.entity.report.ReportCase;
import com.gloryroad.demo.entity.report.ReportInterfac;

import java.util.List;

public class ReportCaseDto extends ReportCase {
    private List<ReportInterfac> reportInterfacs;

    public List<ReportInterfac> getReportInterfacs() {
        return reportInterfacs;
    }

    public void setReportInterfacs(List<ReportInterfac> reportInterfacs) {
        this.reportInterfacs = reportInterfacs;
    }
}
