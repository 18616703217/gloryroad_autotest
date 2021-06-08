package com.gloryroad.demo.service.engine;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.dto.report.ReportBasicDto;
import com.gloryroad.demo.dto.report.ReportCaseDto;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;
import com.gloryroad.demo.entity.engine.EngineActionBasic;
import com.gloryroad.demo.entity.report.ReportInterfac;
import com.gloryroad.demo.service.report.ReportBasicService;
import com.gloryroad.demo.utils.TimesUtil;
import com.gloryroad.demo.utils.engine.OKHttpUtil;
import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class TaskActionService {
    @Autowired
    private TaskActionDetailService taskActionDetailService;

    @Autowired
    private ReportBasicService reportBasicService;


    /** 执行任务入口 */
    public ReportBasicDto actonMain(HttpServletRequest request){
        EngineActionBasic engineActionBasic = taskActionDetailService.getTaskAction(request);
        if(engineActionBasic == null){
            return null;
        }

        List<CasesBasicDto> casesBasicDtos = taskActionDetailService.getTaskActionContent(engineActionBasic, request);

        if(casesBasicDtos == null){
            return null;
        }

        ReportBasicDto reportBaseDto = new ReportBasicDto();
        List<ReportCaseDto> reportCaseDtos = Lists.newArrayList();
        List<String> errmsgs = Lists.newArrayList();

        reportBaseDto.setReportName(String.format("任务%s-%s", engineActionBasic.getId(), TimesUtil.nowDateToYMD()));
        reportBaseDto.setCreateTime(System.currentTimeMillis());
        reportBaseDto.setTaskId(engineActionBasic.getId().toString());
        reportBaseDto.setStartTime(System.currentTimeMillis());
        reportBaseDto.setGroupId(engineActionBasic.getGroupId());
        reportBaseDto.setExecutionEnv("");
        reportBaseDto.setCreateAccount(engineActionBasic.getCreateAccount());

        for(CasesBasicDto casesBasicDto: casesBasicDtos){
            ReportCaseDto reportCaseDto = new ReportCaseDto();
            List<ReportInterfac> reportInterfacs = Lists.newArrayList();

            reportCaseDto.setCaseId(casesBasicDto.getId());
            reportCaseDto.setCaseName(casesBasicDto.getCaseName());
            reportCaseDto.setCaseRemark(casesBasicDto.getRemark());

            for(CasesInterfacDto casesInterfacDto: casesBasicDto.getCasesInterfacDtos()){
                OKHttpUtil okHttpUtil = OKHttpUtil.getInstance();
                ReportInterfac reportInterfac = new ReportInterfac();
                reportInterfac.setUrl(casesInterfacDto.getUrl());
                reportInterfac.setRemark(casesInterfacDto.getRemark());
                reportInterfac.setInterfacName(casesInterfacDto.getInterfacName());
                reportInterfac.setStepNum(casesInterfacDto.getStepNum());
                reportInterfac.setAsserts(JSONObject.toJSONString(casesInterfacDto.getCasesInterfacAsserts()));
                reportInterfac.setHeaders(casesInterfacDto.getInterfacHeaderData());
                reportInterfac.setJsons(casesInterfacDto.getInterfacJsonData());
                reportInterfac.setQueryDatas(casesInterfacDto.getInterfacQueryData());
                reportInterfac.setForms(casesInterfacDto.getInterfacFormData());

                String responesString = null;

                casesInterfacDto.setInterfacFormData(actonHandleParamsMain(casesInterfacDto.getInterfacFormData()));
                casesInterfacDto.setInterfacJsonData(actonHandleParamsMain(casesInterfacDto.getInterfacJsonData()));
                casesInterfacDto.setInterfacQueryData(actonHandleParamsMain(casesInterfacDto.getInterfacQueryData()));

                if(casesInterfacDto.getMethodType() == GloryRoadEnum.CaseSubMethod.GET){

                    responesString = okHttpUtil.doGet(casesInterfacDto.getUrl(), casesInterfacDto.getInterfacQueryData());

                }

                if(casesInterfacDto.getMethodType() == GloryRoadEnum.CaseSubMethod.POST){
                    if(casesInterfacDto.getBodyType() == GloryRoadEnum.CaseBodyType.JSON){
                        responesString = okHttpUtil.doPostJson(casesInterfacDto.getUrl(), casesInterfacDto.getInterfacQueryData(), casesInterfacDto.getInterfacJsonData());
                    }

                    if(casesInterfacDto.getBodyType() == GloryRoadEnum.CaseBodyType.FORM){
                        responesString = okHttpUtil.doPostForm(casesInterfacDto.getUrl(), casesInterfacDto.getInterfacQueryData(), casesInterfacDto.getInterfacJsonData());
                    }
                }

                reportInterfac.setResponse(responesString);

                Map<String, String> message = Maps.newHashMap();
                int code = actonAssertMain(responesString, casesInterfacDto.getCasesInterfacAsserts(), message);

                if(code != ResCode.C0){
                    reportInterfac.setRunState(GloryRoadEnum.RunStatus.EXE_FAILD);
                    errmsgs.add(message.get("errmsg"));
                    reportInterfacs.add(reportInterfac);
                    break;
                }

                // 缓存每一个接口的执行结果
                taskActionDetailService.setTaskRunCache(engineActionBasic.getId(),
                        casesBasicDto.getId(), casesInterfacDto.getStepNum(), responesString);

                reportInterfac.setRunState(GloryRoadEnum.RunStatus.EXE_SUCESS);
                reportInterfacs.add(reportInterfac);
            }
            reportCaseDto.setReportInterfacs(reportInterfacs);
        }

        Map<String, String> messageMap = Maps.newHashMap();
        reportBasicService.insertReportBasic(reportBaseDto, messageMap, request);
        return reportBaseDto;
    }

    /** 执行断言入口 */
    public int actonAssertMain(String responesString, List<CasesInterfacAssert> asserts, Map<String, String> message){
        return ResCode.C0;
    }

    /** 参数处理 */
    public JSONObject actonHandleParamsMain(JSONObject params){
        return params;
    }
}
