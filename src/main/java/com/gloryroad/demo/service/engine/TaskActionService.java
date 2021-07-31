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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskActionService{
    @Autowired
    private TaskActionDetailService taskActionDetailService;

    @Autowired
    private ReportBasicService reportBasicService;


    /** 执行任务入口 */
    public void actionMain(){
        EngineActionBasic engineActionBasic = taskActionDetailService.getTaskAction();
        if(engineActionBasic == null){
            return;
        }

        List<CasesBasicDto> casesBasicDtos = taskActionDetailService.getTaskActionContent(engineActionBasic);

        if(casesBasicDtos == null){
            return;
        }

        ReportBasicDto reportBaseDto = new ReportBasicDto();
        List<ReportCaseDto> reportCaseDtos = Lists.newArrayList();
        List<String> errmsgs = Lists.newArrayList();

        reportBaseDto.setReportName(String.format("任务%s-%s", engineActionBasic.getId(), TimesUtil.nowDateToYMD()));
        reportBaseDto.setTaskId(engineActionBasic.getId().toString());
        reportBaseDto.setStartTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));
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
                reportInterfac.setCasesInterfacId(casesInterfacDto.getId());
                reportInterfac.setMethodType(casesInterfacDto.getMethodType());
                reportInterfac.setUrl(casesInterfacDto.getUrl());
                reportInterfac.setRemark(casesInterfacDto.getRemark());
                reportInterfac.setInterfacName(casesInterfacDto.getInterfacName());
                reportInterfac.setStepNum(casesInterfacDto.getStepNum());
                reportInterfac.setAsserts(JSONObject.toJSONString(casesInterfacDto.getInterfacAsserts()));
                reportInterfac.setHeaders(JSONObject.toJSONString(casesInterfacDto.getInterfacHeaderData()));
                reportInterfac.setJsons(JSONObject.toJSONString(casesInterfacDto.getInterfacJsonData()));
                reportInterfac.setQueryDatas(JSONObject.toJSONString(casesInterfacDto.getInterfacQueryData()));
                reportInterfac.setForms(JSONObject.toJSONString(casesInterfacDto.getInterfacFormData()));

                String responesString = null;

                // 请求上下文数据替换
                casesInterfacDto.setInterfacFormData(actonHandleParamsMain(casesInterfacDto.getInterfacFormData(),
                        engineActionBasic.getId(), casesBasicDto.getId()));
                casesInterfacDto.setInterfacJsonData(actonHandleParamsMain(casesInterfacDto.getInterfacJsonData(),
                        engineActionBasic.getId(), casesBasicDto.getId()));
                casesInterfacDto.setInterfacQueryData(actonHandleParamsMain(casesInterfacDto.getInterfacQueryData(),
                        engineActionBasic.getId(), casesBasicDto.getId()));
                casesInterfacDto.setInterfacHeaderData(actonHandleParamsMain(casesInterfacDto.getInterfacHeaderData(),
                        engineActionBasic.getId(), casesBasicDto.getId()));

                // 发送请求
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
                int code = actonAssertMain(responesString, casesInterfacDto.getInterfacAsserts(), message);

                if(code != ResCode.C0){
                    reportInterfac.setRunState(GloryRoadEnum.RunStatus.EXE_FAILD);
                    reportCaseDto.setRunState(GloryRoadEnum.RunStatus.EXE_FAILD);
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

            // 最终结果写入
            reportCaseDto.setReportInterfacs(reportInterfacs);
            reportCaseDto.setRunState(GloryRoadEnum.RunStatus.EXE_SUCESS);
            reportCaseDtos.add(reportCaseDto);
        }
        reportBaseDto.setReportCaseDtos(reportCaseDtos);

        Map<String, String> messageMap = Maps.newHashMap();
        reportBasicService.insertReportBasic(reportBaseDto, messageMap);
    }

    /** 执行断言入口 */
    public int actonAssertMain(String responesString, List<CasesInterfacAssert> asserts, Map<String, String> message){
        if(responesString == null){
            return ResCode.A1001;
        }

        if(asserts == null || asserts.size() == 0){
            return ResCode.C0;
        }

        for(CasesInterfacAssert casesInterfacAssert: asserts){
            if(casesInterfacAssert.getAssertPosition() == GloryRoadEnum.AssertPosition.BODY){
                if(! responesString.contains(casesInterfacAssert.getAssertContent())){
                    return ResCode.A1001;
                }
            }
        }

        return ResCode.C0;
    }

    /** 参数处理 */
    public JSONObject actonHandleParamsMain(JSONObject params, Integer taskId, Integer caseId){

        if(params.isEmpty()){
            return params;
        }

        String paramsPattern = "\\$(.+)\\$";
        String responsePattern = "";
        Integer stepNum;
        String paramsString = params.toJSONString();

        Pattern parmp = Pattern.compile(paramsPattern);
        Matcher parmm = parmp.matcher(paramsString);

        Pattern resp = null;
        Matcher resm = null;

        String replaceData;
        // 查找参数中需要替换的数据
        while (parmm.find()){
            String[] inputResult = parmm.group(1).split("-");
            try{
                stepNum = Integer.valueOf(inputResult[0]);
            }catch (Exception e){
                return JSONObject.parseObject(paramsString);
            }

            String response = taskActionDetailService.getTaskRunCache(taskId, caseId, stepNum);

            responsePattern = inputResult[1];
            resp = Pattern.compile(responsePattern);
            resm = resp.matcher(response);

            // 最终替换
            if(resm.find()){
                replaceData = resm.group();
                paramsString = parmm.replaceFirst(replaceData);
            }

        }

        return JSONObject.parseObject(paramsString);
    }

}
