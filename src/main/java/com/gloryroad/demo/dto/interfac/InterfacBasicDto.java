package com.gloryroad.demo.dto.interfac;

import com.gloryroad.demo.entity.interfac.InterfacBasic;

/**
 * 用户页面展示对象
 */
public class InterfacBasicDto extends InterfacBasic {
    private String groupName;
    private String createUserName;

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
}
