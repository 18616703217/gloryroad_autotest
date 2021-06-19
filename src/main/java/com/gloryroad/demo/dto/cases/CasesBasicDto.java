package com.gloryroad.demo.dto.cases;

import com.gloryroad.demo.entity.cases.CasesBasic;

import java.util.List;

public class CasesBasicDto extends CasesBasic {
    private String groupName;

    private String createUserName;

    private String createDate;

    private List<CasesInterfacDto> casesInterfacDtos;

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

    public List<CasesInterfacDto> getCasesInterfacDtos() {
        return casesInterfacDtos;
    }

    public void setCasesInterfacDtos(List<CasesInterfacDto> casesInterfacDtos) {
        this.casesInterfacDtos = casesInterfacDtos;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
