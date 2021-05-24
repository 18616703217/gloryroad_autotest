package com.gloryroad.demo.Vo.system;

import com.gloryroad.demo.Vo.PageModel;

public class SystemUserQueryVo extends PageModel {
    /** 主键*/
    private Integer id;

    // 账号
    private String account;
    // 姓名
    private String name;

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
}
