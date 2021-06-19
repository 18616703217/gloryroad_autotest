package com.gloryroad.demo.entity.cases;

import com.gloryroad.demo.constant.GloryRoadEnum;

public class CasesInterfacAssert {
    /** 主键 */
    private Integer id;

    /** 接口id */
    private Integer casesInterfacId;

    /** 断言位置 */
    private GloryRoadEnum.AssertPosition assertPosition;

    /** 断言内容 */
    private String assertContent;

    /** 创建账户 */
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

    public Integer getCasesInterfacId() {
        return casesInterfacId;
    }

    public void setCasesInterfacId(Integer casesInterfacId) {
        this.casesInterfacId = casesInterfacId;
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

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
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
}
