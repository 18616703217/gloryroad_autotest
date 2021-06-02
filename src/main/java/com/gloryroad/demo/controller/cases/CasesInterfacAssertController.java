package com.gloryroad.demo.controller.cases;

import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;
import com.gloryroad.demo.service.cases.CasesInterfacAssertService;
import com.gloryroad.demo.service.cases.CasesInterfacService;
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
@RequestMapping(value = "/cases/assert")
public class CasesInterfacAssertController extends BaseController {
    
    @Autowired
    private CasesInterfacAssertService casesInterfacAssertService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel insert(List<CasesInterfacAssert> casesInterfacAsserts, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesInterfacAssertService.insertCasesInterfacAsserts(casesInterfacAsserts, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(List<CasesInterfacAssert> casesInterfacAsserts, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesInterfacAssertService.updateCasesInterfacAsserts(casesInterfacAsserts, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel delete(@RequestParam("ids") Integer[] ids, HttpServletRequest request) {

        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesInterfacAssertService.deleteCasesInterfacAsserts(ids, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
