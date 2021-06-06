package com.gloryroad.demo.Vo.task;

import com.gloryroad.demo.Vo.PageModel;

public class TaskBasicQueryVo extends PageModel {
    /** 主键*/
    private Integer id;

    /** 接口名称 */
    private String taskName;

    /** 业务分组id */
    private Integer groupId;

    /** 创建人账号 */
    private String createAccount;

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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
