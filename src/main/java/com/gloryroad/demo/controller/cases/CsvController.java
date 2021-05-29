package com.gloryroad.demo.controller.cases;

import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.controller.base.BaseController;
import com.gloryroad.demo.service.cases.CsvService;
import com.gloryroad.demo.service.system.SystemGroupService;
import com.gloryroad.demo.utils.CsvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/cases/csv")
public class CsvController extends BaseController {


    @Autowired
    private CsvService csvService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseModel downloadCsv(@RequestParam("file_name") String fileName,
                                     HttpServletResponse response, HttpServletRequest request){
        int code = csvService.downloadCsv(fileName, response, request);
        if (code == ResCode.C0){
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, "下载文件失败");
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel uploadCsv(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        int code = csvService.uploadCsv(multipartFile, request);
        if (code == ResCode.C0){
            return ResponseModel.returnSuccess();
        }
        return ResponseModel.returnFail(code, "上传文件失败");
    }

}
