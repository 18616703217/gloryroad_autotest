package com.gloryroad.demo.controller.task;

import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.entity.engine.EngineActionBasic;
import com.gloryroad.demo.service.engine.TaskActionDetailService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/task/action")
public class TaskActionController extends BaseController{
    @Autowired
    private TaskActionDetailService taskActionService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel taskActionMain(@RequestBody EngineActionBasic engineActionBasic, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();

        int code = taskActionService.insertTaskAction(request, engineActionBasic, messageMap);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel deleteTaskAction(@RequestParam("task_id") Integer taskId, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();

        int code = taskActionService.deleteTaskAction(taskId, request, messageMap);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
