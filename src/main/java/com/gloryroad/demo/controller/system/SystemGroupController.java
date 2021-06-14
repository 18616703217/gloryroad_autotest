package com.gloryroad.demo.controller.system;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.system.SystemGroupQueryVo;
import com.gloryroad.demo.Vo.system.SystemGroupQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.entity.system.SystemGroup;
import com.gloryroad.demo.service.system.SystemGroupService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/system/group")
public class SystemGroupController extends BaseController {
    @Autowired
    private SystemGroupService systemGroupService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<PageModel<SystemGroup>> find(SystemGroupQueryVo systemGroupQueryVo, HttpServletRequest request) {
        try {
            PageModel<SystemGroup> page = systemGroupService.findSystemGroups(systemGroupQueryVo, request);
            return ResponseModel.returnSuccess(page);
        }catch (Exception e){
            return ResponseModel.returnFail(ResCode.C1008, "查询分组信息失败");
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel insert(@RequestBody SystemGroup systemGroup, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = systemGroupService.insertSystemGroups(systemGroup, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(@RequestBody SystemGroup systemGroup, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = systemGroupService.updateSystemGroup(systemGroup, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel delete(@RequestParam("ids") String[] ids, HttpServletRequest request) {

        Map<String, String> messageMap = Maps.newHashMap();
        int code = systemGroupService.deleteSystemGroups(ids, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
