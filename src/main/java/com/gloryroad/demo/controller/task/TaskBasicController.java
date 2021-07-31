package com.gloryroad.demo.controller.task;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.system.SystemGroupQueryVo;
import com.gloryroad.demo.Vo.task.TaskBasicQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.dto.TaskBasicDto;
import com.gloryroad.demo.entity.system.SystemGroup;
import com.gloryroad.demo.entity.task.TaskBasic;
import com.gloryroad.demo.service.system.SystemGroupService;
import com.gloryroad.demo.service.task.TaskBasicService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(value = "/task/basic")
public class TaskBasicController extends BaseController {
    @Autowired
    private TaskBasicService taskBasicService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<PageModel<TaskBasicDto>> find(TaskBasicQueryVo taskBasicQueryVo, HttpServletRequest request) {
        try {
            PageModel<TaskBasicDto> page = taskBasicService.finds(taskBasicQueryVo, request);
            return ResponseModel.returnSuccess(page);
        }catch (Exception e){
            return ResponseModel.returnFail(ResCode.C1008, "查询任务信息失败");
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel insert(@RequestBody TaskBasic taskBasic, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = taskBasicService.insertTaskBasics(taskBasic, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(@RequestBody TaskBasic taskBasic, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = taskBasicService.updateInterfacBasic(taskBasic, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel delete(@RequestParam("ids") Integer[] ids, HttpServletRequest request) {

        Map<String, String> messageMap = Maps.newHashMap();
        int code = taskBasicService.deleteTaskBasics(ids, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
