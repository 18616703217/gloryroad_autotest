package com.gloryroad.demo.entity.report;

import com.gloryroad.demo.constant.GloryRoadEnum;

public class ReportCase {

    private Integer id;

    private Integer casesId;

    private Integer reportBaseId;

    /** 用例名称 */
    private String caseName;

    /** 用例备注 */
    private String caseRemark;

    /** step运行状态 */
    private GloryRoadEnum.RunStatus runState;

    /** 创建时间 */
    private long createTime;

    /** 状态 */
    private Integer status;
}
