package com.gloryroad.demo.dto;

import com.gloryroad.demo.entity.cases.CasesBasic;
import com.gloryroad.demo.entity.task.TaskBasic;

import java.util.List;

public class TaskBasicDto extends TaskBasic {
    private String groupName;

    private String createUserName;

    private String createDate;

    private List<CasesBasic> casesBasicList;

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<CasesBasic> getCasesBasicList() {
        return casesBasicList;
    }

    public void setCasesBasicList(List<CasesBasic> casesBasicList) {
        this.casesBasicList = casesBasicList;
    }
}
