package com.gloryroad.demo.controller.cases;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.cases.CasesBasicQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.entity.cases.CasesBasic;
import com.gloryroad.demo.service.cases.CasesBasicService;
import com.gloryroad.demo.service.cases.CasesInterfacService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cases/interfac")
public class CasesInterfacController extends BaseController {
    
    @Autowired
    private CasesInterfacService casesInterfacService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<CasesInterfacDto> find(@RequestParam("id") Integer id, HttpServletRequest request) {
        try {
            CasesInterfacDto casesInterfacDto = casesInterfacService.findById(id, request);
            return ResponseModel.returnSuccess(casesInterfacDto);
        }catch (Exception e){
            System.out.println("e = " + e.toString());
            return ResponseModel.returnFail(ResCode.C1008, "查询信息用例失败");
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel insert(@RequestBody List<CasesInterfacDto> casesInterfacDtos, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesInterfacService.insertCasesInterfacs(casesInterfacDtos, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel update(@RequestBody List<CasesInterfacDto> casesInterfacDtos, HttpServletRequest request) {
        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesInterfacService.updateCasesInterfac(casesInterfacDtos, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }

    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel delete(@RequestParam("id") Integer id, HttpServletRequest request) {

        Map<String, String> messageMap = Maps.newHashMap();
        int code = casesInterfacService.deleteCasesInterfacs(id, messageMap, request);
        if(code == ResCode.C0) {
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, messageMap.get("errmsg"));
    }
}
