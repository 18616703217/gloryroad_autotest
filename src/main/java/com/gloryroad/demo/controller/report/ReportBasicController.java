package com.gloryroad.demo.controller.report;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.cases.CasesBasicQueryVo;
import com.gloryroad.demo.Vo.report.ReportBasicQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.dto.report.ReportBasicDto;
import com.gloryroad.demo.entity.cases.CasesBasic;
import com.gloryroad.demo.entity.report.ReportBasic;
import com.gloryroad.demo.service.cases.CasesBasicService;
import com.gloryroad.demo.service.report.ReportBasicService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/report/basic")
public class ReportBasicController extends BaseController {
    
    @Autowired
    private ReportBasicService reportBasicService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<PageModel<ReportBasicDto>> find(ReportBasicQueryVo reportBasicQueryVo, HttpServletRequest request) {
        try {
            PageModel<ReportBasicDto> page = reportBasicService.findReportBasic(reportBasicQueryVo, request);
            return ResponseModel.returnSuccess(page);
        }catch (Exception e){
            return ResponseModel.returnFail(ResCode.C1008, "查询信息报告失败");
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel insert(ReportBasicDto reportBasicDto, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = reportBasicService.insertReportBasic(reportBasicDto, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(ReportBasicDto reportBasicDto, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = reportBasicService.updateReportBasic(reportBasicDto, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel delete(@RequestParam("ids") Integer[] ids, HttpServletRequest request) {

        Map<String, String> messageMap = Maps.newHashMap();
        int code = reportBasicService.deleteReportBasics(ids, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
