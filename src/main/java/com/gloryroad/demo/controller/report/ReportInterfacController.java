package com.gloryroad.demo.controller.report;

import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.dto.report.ReportCaseDto;
import com.gloryroad.demo.entity.report.ReportInterfac;
import com.gloryroad.demo.service.report.ReportCaseService;
import com.gloryroad.demo.service.report.ReportInterfacService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/report/interfac")
public class ReportInterfacController extends BaseController {
    
    @Autowired
    private ReportInterfacService reportInterfacService;

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(List<ReportInterfac> reportInterfacs, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = reportInterfacService.updateReportInterfacs(reportInterfacs, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
