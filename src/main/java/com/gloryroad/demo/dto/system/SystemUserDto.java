package com.gloryroad.demo.dto.system;

import com.gloryroad.demo.entity.system.SystemUser;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SystemUserDto extends SystemUser {
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
