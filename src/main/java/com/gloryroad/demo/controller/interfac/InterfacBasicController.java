package com.gloryroad.demo.controller.interfac;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.Vo.system.SystemUserQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.gloryroad.demo.entity.system.SystemUser;
import com.gloryroad.demo.service.interfac.InterfacBasicService;
import com.gloryroad.demo.service.system.SystemUserService;
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
@RequestMapping(value = "/interfac/basic")
public class InterfacBasicController extends BaseController {
    
    @Autowired
    private InterfacBasicService interfacBasicService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<PageModel<InterfacBasic>> find(InterfacBasicQueryVo interfacBasicQueryVo, HttpServletRequest request) {
        try {
            PageModel<InterfacBasic> page = interfacBasicService.findInterfacBasics(interfacBasicQueryVo, request);
            return ResponseModel.returnSuccess(page);
        }catch (Exception e){
            return ResponseModel.returnFail(ResCode.C1008, "查询接口信息失败");
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel insert(InterfacBasic interfacBasic, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = interfacBasicService.insertInterfacBasics(interfacBasic, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(InterfacBasic interfacBasic, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = interfacBasicService.updateInterfacBasic(interfacBasic, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel delete(@RequestParam("ids") String[] ids, HttpServletRequest request) {

        Map<String, String> messageMap = Maps.newHashMap();
        int code = interfacBasicService.deleteInterfacBasics(ids, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
