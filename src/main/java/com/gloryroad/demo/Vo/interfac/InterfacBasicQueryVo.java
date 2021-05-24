package com.gloryroad.demo.Vo.interfac;

import com.gloryroad.demo.Vo.PageModel;

public class InterfacBasicQueryVo extends PageModel {
    /** 主键*/
    private Integer id;

    /** 接口名称 */
    private String interfacName;

    /** 业务分组id */
    private Integer groupId;

    /** 创建人账号 */
    private String createAccount;

    public String getInterfacName() {
        return interfacName;
    }

    public void setInterfacName(String interfacName) {
        this.interfacName = interfacName;
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
