package com.gloryroad.demo.Vo.report;

import com.gloryroad.demo.Vo.PageModel;

public class ReportBasicQueryVo extends PageModel {
    /** 主键*/
    private Integer id;

    /** 接口名称 */
    private String reportName;

    /** 业务分组id */
    private Integer groupId;

    /** 创建人账号 */
    private String createAccount;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
