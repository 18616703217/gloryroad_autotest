package com.gloryroad.demo.entity.report;

import com.gloryroad.demo.constant.GloryRoadEnum;

public class ReportBase {

    private Integer id;

    private String taskNo;

    /** 关联业务分组id */
    private Integer groupId;

    /** 创建账号 */
    private String createAccount;

    private String reportNo;
    private String reportName;
    private GloryRoadEnum.ExecutionType executionType;

    private String executionEnv;

    private Integer startTime;

    private Integer endTime;

    /** 任务执行状态，写在报告里面 */
    private GloryRoadEnum.TaskStatus taskStatus;

    /** 失败原因 */
    private String errMsg;

    private long createTime;

    /** 创建人邮箱 */
    private String mail;

    /** 状态 */
    private Integer status;



}
