package com.gloryroad.demo.controller.interfac;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.interfac.InterfacAssert;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.gloryroad.demo.service.interfac.InterfacAssertService;
import com.gloryroad.demo.service.interfac.InterfacBasicService;
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
@RequestMapping(value = "/interfac/assert")
public class InterfacAssertController extends BaseController {
    
    @Autowired
    private InterfacAssertService interfacAssertService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<List<InterfacAssert>> find(Integer interfacId, HttpServletRequest request) {
        try {
            List<InterfacAssert> interfacAsserts = interfacAssertService.findInterfacAsserts(interfacId, request);
            return ResponseModel.returnSuccess(interfacAsserts);
        }catch (Exception e){
            return ResponseModel.returnFail(ResCode.C1008, "查询接口信息失败");
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel insert(InterfacAssert interfacAssert, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = interfacAssertService.insertInterfacAssert(interfacAssert, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(InterfacAssert interfacAssert, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = interfacAssertService.updateInterfacAssert(interfacAssert, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel delete(@RequestParam("ids") Integer[] ids, HttpServletRequest request) {

        Map<String, String> messageMap = Maps.newHashMap();
        int code = interfacAssertService.deleteInterfacAsserts(ids, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
