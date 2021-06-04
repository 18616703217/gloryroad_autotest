package com.gloryroad.demo.entity.engine;

import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.entity.task.TaskBasic;

import java.util.List;

public class EngineActionBasic extends TaskBasic {

    /** 创建人邮件 */
    private String mail;

    /** 执行模式 */
    private GloryRoadEnum.RunMode runMode;

    /** 执行开始时间 */
    private long startTime;

    /** 执行模式具体内容 */
    private Integer runModeContent;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public GloryRoadEnum.RunMode getRunMode() {
        return runMode;
    }

    public void setRunMode(GloryRoadEnum.RunMode runMode) {
        this.runMode = runMode;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Integer getRunModeContent() {
        return runModeContent;
    }

    public void setRunModeContent(Integer runModeContent) {
        this.runModeContent = runModeContent;
    }
}
