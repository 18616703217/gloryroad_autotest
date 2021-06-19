package com.gloryroad.demo.dto.system;

import com.gloryroad.demo.entity.system.SystemUser;

/**
 * 用户页面展示对象
 */
public class SystemUserDto extends SystemUser {
    private String groupName;

    private String createDate;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
