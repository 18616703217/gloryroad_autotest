package com.gloryroad.demo.entity.report;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;

import java.util.List;
import java.util.Map;

public class ReportInterfac {

    private Integer id;
    private Integer reportCaseId;

    /** 接口ID */
    private Integer casesInterfacId;

    /** 接口名称 */
    private String interfacName;

    /** 执行步骤 */
    private Integer stepNum;
    /**
     * 真正运行的url
     */
    private String url;

    /**
     * step 备注
     */
    private String remark;

    /**
     * 提交方式（get/post）
     */
    private GloryRoadEnum.CaseSubMethod methodType;

    /**
     * form（提交时的真实参数）
     */
    private JSONObject forms;

    /**
     * header（提交时的真实参数）
     */
    private JSONObject headers;

    /**
     * json（提交时的真实参数）
     */
    private JSONObject json;

    /**
     * 断言内容
     */
    private List<CasesInterfacAssert> casesInterfacAsserts;

    /** step运行状态 */
    private GloryRoadEnum.RunStatus runState;

}
