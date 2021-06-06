package com.gloryroad.demo.service.engine;

import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.dto.report.ReportBaseDto;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TaskActionService {
    @Autowired
    private TaskActionDetailService taskActionDetailService;


    /** 执行任务入口 */
    public List<ReportBaseDto> actonMain(HttpServletRequest request){
        List<ReportBaseDto> reportBaseDtos = Lists.newArrayList();
        return reportBaseDtos;
    }
}
