package com.gloryroad.demo.dto;

import com.gloryroad.demo.entity.task.TaskBasic;

public class TaskBasicDto extends TaskBasic {
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
