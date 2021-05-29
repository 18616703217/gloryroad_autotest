package com.gloryroad.demo.service.cases;

import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.service.system.SystemGroupService;
import com.gloryroad.demo.utils.CsvUtils;
import com.gloryroad.demo.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

@Service
public class CsvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvService.class);

    public int downloadCsv(String fileName, HttpServletResponse response, HttpServletRequest request){

        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("downloadCsv ip {}", ip);

        File file;
        List<List<String>> values = CsvUtils.readCSV(fileName);
        try{
            file = CsvUtils.makeTempCSV(fileName, values);
        } catch (IOException e){
            return ResCode.C1008;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName +".csv");
        CsvUtils.downloadFile(response, file);
        return ResCode.C0;
    }

    public int uploadCsv(MultipartFile multipartFile, HttpServletRequest request){

        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("uploadCsv ip {}", ip);

        try {
            //上传内容不能为空
            if (multipartFile.isEmpty()) {
                return ResCode.C1001;
            }
            File file = CsvUtils.uploadFile(multipartFile);
            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeFields();
            file.delete();
            return ResCode.C0;
        } catch (Exception e) {
            e.printStackTrace();
            return ResCode.C1008;
        }
    }

}
