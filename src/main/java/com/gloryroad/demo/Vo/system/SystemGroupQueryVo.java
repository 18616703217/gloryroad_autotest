package com.gloryroad.demo.Vo.system;

import com.gloryroad.demo.Vo.PageModel;

public class SystemGroupQueryVo extends PageModel {
    /** 主键 */
    private Integer id;

    /** 组名称 */
    private String groupName;
    /** 组标示 */
    private String groupSign;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupSign() {
        return groupSign;
    }

    public void setGroupSign(String groupSign) {
        this.groupSign = groupSign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
