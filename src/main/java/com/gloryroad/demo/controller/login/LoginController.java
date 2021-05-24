package com.gloryroad.demo.controller.login;

import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.service.login.LoginService;
import com.gloryroad.demo.service.system.SystemUserService;
import com.gloryroad.demo.utils.session.UserUtil;
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
@RequestMapping(value = "/system/login")
public class LoginController {
    @Autowired
    private UserUtil userUtil;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel login(@RequestParam("account") String account, @RequestParam("passwd") String passwd,
                              HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = loginService.checkSystemUser(account, passwd, messageMap, request);
        if(code == 0){
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel logout(HttpServletRequest request) {
        int code = loginService.logout(request);
        if(code == 0){
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, "logout fail");
    }
}
