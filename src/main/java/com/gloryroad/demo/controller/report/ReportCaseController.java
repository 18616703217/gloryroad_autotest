package com.gloryroad.demo.controller.report;

import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.dto.report.ReportCaseDto;
import com.gloryroad.demo.service.cases.CasesInterfacService;
import com.gloryroad.demo.service.report.ReportCaseService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/report/case")
public class ReportCaseController extends BaseController {
    
    @Autowired
    private ReportCaseService reportCaseService;

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(ReportCaseDto reportCaseDto, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = reportCaseService.updateReportCase(reportCaseDto, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
