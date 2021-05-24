package com.gloryroad.demo.entity.session;

import java.util.Map;

public class BuiltInUser implements IUser{
    /** 员工账号 */
    private String account;
    /** 真实姓名 */
    private String name;
    /** 员工职位 */
    private String role;
    /** 员工部门 */
    private String department;
    /** 邮箱 */
    private String mail;
    /** 获取用户权限 */
    private Map<String,Integer> permission;

    @Override
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String getRole() {
        return role;
    }


    @Override
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public Map<String,Integer> getPermission() {
        return permission;
    }

    public void setPermission(Map<String, Integer> permission){
        this.permission = permission;
    }
}
