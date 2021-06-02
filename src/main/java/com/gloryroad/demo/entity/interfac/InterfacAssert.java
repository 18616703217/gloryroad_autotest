package com.gloryroad.demo.entity.interfac;

import com.gloryroad.demo.constant.GloryRoadEnum;

public class InterfacAssert {
    /** 主键 */
    private Integer id;

    /** 接口id */
    private Integer interfacId;

    /** 断言位置 */
    private GloryRoadEnum.AssertPosition assertPosition;

    /** 断言内容 */
    private String assertContent;

    /** 创建账号 */
    private String createAccount;

    /** 状态 */
    private Integer status;

    /** 创建时间 */
    private long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInterfacId() {
        return interfacId;
    }

    public void setInterfacId(Integer interfacId) {
        this.interfacId = interfacId;
    }

    public GloryRoadEnum.AssertPosition getAssertPosition() {
        return assertPosition;
    }

    public void setAssertPosition(GloryRoadEnum.AssertPosition assertPosition) {
        this.assertPosition = assertPosition;
    }

    public String getAssertContent() {
        return assertContent;
    }

    public void setAssertContent(String assertContent) {
        this.assertContent = assertContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }
}
