package com.gloryroad.demo.dto.interfac;

import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.entity.interfac.InterfacAssert;
import com.gloryroad.demo.entity.interfac.InterfacBasic;

import java.util.List;

/**
 * 用户页面展示对象
 */
public class InterfacBasicDto extends InterfacBasic {
    private String groupName;

    private String createUserName;

    /** 断言位置 */
    private List<InterfacAssert> interfacAsserts;

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

    public List<InterfacAssert> getInterfacAsserts() {
        return interfacAsserts;
    }

    public void setInterfacAsserts(List<InterfacAssert> interfacAsserts) {
        this.interfacAsserts = interfacAsserts;
    }
}
