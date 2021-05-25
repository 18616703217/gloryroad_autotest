package com.gloryroad.demo.entity.system;


import com.gloryroad.demo.constant.GloryRoadEnum;

/**
 * 系统用户实体类
 */
public class SystemUser{
    private Integer id;
    private String account;
    private String name;
    private GloryRoadEnum.Role role;
    private String groupSign;
    private Integer groupId;
    private String mail;
    private Integer status;
    private long createTime;



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public GloryRoadEnum.Role getRole() {
        return role;
    }

    public void setRole(GloryRoadEnum.Role role) {
        this.role = role;
    }

    public String getGroupSign() {
        return groupSign;
    }

    public void setGroupSign(String groupSign) {
        this.groupSign = groupSign;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
