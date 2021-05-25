package com.gloryroad.demo.entity.session;

import com.gloryroad.demo.constant.GloryRoadEnum;

import java.util.Map;

public interface IUser {
    /**
     * 用户保存正常登陆录session名
     */
    public static final String SESSION_KEY = "biUser";

    /** 员工账号 */
    public String getAccount();
    /** 真实姓名 */
    public String getName();
    /** 员工职位 */
    public GloryRoadEnum.Role getRole();
    /** 员工部门 */
    public String getDepartment();
    /** 邮箱 */
    public String getMail();
    /** 获取用户权限 */
    public Map<String,Integer> getPermission();
}
