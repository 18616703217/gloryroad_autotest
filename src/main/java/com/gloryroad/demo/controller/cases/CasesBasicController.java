package com.gloryroad.demo.controller.cases;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.cases.CasesBasicQueryVo;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.cases.CasesBasic;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.gloryroad.demo.service.cases.CasesBasicService;
import com.gloryroad.demo.service.cases.CasesInterfacService;
import com.gloryroad.demo.service.interfac.InterfacBasicService;
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
@RequestMapping(value = "/cases/basic")
public class CasesBasicController extends BaseController {
    
    @Autowired
    private CasesBasicService casesBasicService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<PageModel<CasesBasicDto>> find(CasesBasicQueryVo casesBasicQueryVo, HttpServletRequest request) {
        try {
            PageModel<CasesBasicDto> page = casesBasicService.findCasesBasics(casesBasicQueryVo, request);
            return ResponseModel.returnSuccess(page);
        }catch (Exception e){
            return ResponseModel.returnFail(ResCode.C1008, "查询信息用例失败");
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel insert(CasesBasic casesBasic, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesBasicService.insertCasesBasic(casesBasic, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(CasesBasic casesBasic, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesBasicService.updateCasesBasic(casesBasic, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel delete(@RequestParam("ids") Integer[] ids, HttpServletRequest request) {

        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesBasicService.deleteCasesBasics(ids, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "/copy",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel copy(@RequestParam("id") Integer id, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesBasicService.copyCasesBasic(id, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
