package com.gloryroad.demo.controller.system;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.system.SystemUserQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.dto.system.SystemUserDto;
import com.gloryroad.demo.entity.system.SystemUser;
import com.gloryroad.demo.service.system.SystemUserService;
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
@RequestMapping(value = "/system/user")
public class SystemUserController extends BaseController {
    @Autowired
    private SystemUserService systemUserService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<PageModel<SystemUserDto>> find(SystemUserQueryVo systemUserQueryVo, HttpServletRequest request) {
        try {
            PageModel<SystemUserDto> page = systemUserService.findSystemUsers(systemUserQueryVo, request);
            return ResponseModel.returnSuccess(page);
        }catch (Exception e){
            return ResponseModel.returnFail(ResCode.C1008, "查询用户信息失败");
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel insert(SystemUser systemUser, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = systemUserService.insertSystemUsers(systemUser, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(SystemUser systemUser, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = systemUserService.updateSystemUser(systemUser, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel delete(@RequestParam("ids") String[] ids, HttpServletRequest request) {

        Map<String, String> messageMap = Maps.newHashMap();
        int code = systemUserService.deleteSystemUsers(ids, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
