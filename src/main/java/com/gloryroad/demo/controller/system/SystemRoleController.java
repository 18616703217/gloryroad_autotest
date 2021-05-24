package com.gloryroad.demo.controller.system;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.system.SystemUserQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.entity.system.SystemUser;
import com.gloryroad.demo.service.system.SystemRoleService;
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
@RequestMapping(value = "/system/role")
public class SystemRoleController extends BaseController {
    @Autowired
    private SystemRoleService systemRoleService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<List<String>> find(HttpServletRequest request) {
        try {
            List<String> list = systemRoleService.findSystemRoles(request);
            return ResponseModel.returnSuccess(list);
        }catch (Exception e){
            return ResponseModel.returnFail(ResCode.C1008, "查询角色失败");
        }
    }
}
