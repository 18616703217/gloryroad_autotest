package com.gloryroad.demo.service.report;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.cases.CasesInterfacDao;
import com.gloryroad.demo.dao.report.ReportCaseDao;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.dto.report.ReportCaseDto;
import com.gloryroad.demo.entity.cases.CasesInterfac;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;
import com.gloryroad.demo.entity.report.ReportCase;
import com.gloryroad.demo.entity.report.ReportInterfac;
import com.gloryroad.demo.service.cases.CasesInterfacAssertService;
import com.gloryroad.demo.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class ReportCaseService {

    @Autowired
    private ReportCaseDao reportCaseDao;

    @Autowired
    private ReportInterfacService reportInterfacService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportCaseService.class);

    /** 报告用例信息查找 */
    public List<ReportCaseDto> findByReportId(Integer reportBasicId, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} reportBasicId {}", ip, reportBasicId);
        List<ReportCaseDto> casesInterfacDtos = reportCaseDao.getByReportId(reportBasicId);

        for(ReportCaseDto reportCaseDto: casesInterfacDtos){
            reportCaseDto.setReportInterfacs(reportInterfacService.findByReportCaseId(reportCaseDto.getId(), request));
        }
        return casesInterfacDtos;
    }

    /** 报告用例信息插入 */
    public int insertReportCasess(List<ReportCaseDto> reportCaseDtos, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} reportCaseDtos {}", ip, JSON.toJSONString(reportCaseDtos));
        Integer newId;
        if(reportCaseDtos == null
                || reportCaseDtos.size() == 0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        
        try {
            for(ReportCaseDto reportCaseDto: reportCaseDtos) {
                newId = reportCaseDao.insertReportCase(reportCaseDto);
                
                for(ReportInterfac reportInterfac: reportCaseDto.getReportInterfacs()){
                    reportInterfac.setReportCaseId(newId);
                }

                int code = reportInterfacService.insertReportInterfacs(reportCaseDto.getReportInterfacs(), messageMap, request);

                if(code != ResCode.C0){
                    return ResCode.C1008;
                }
            }
            return ResCode.C0;

        }catch (NullPointerException e){
            LOGGER.error("insertCasesInterfacBasics 执行失败 {}", e.toString());
        }
        messageMap.put("errmsg", "插入报告用例信息失败");
        return ResCode.C1008;
    }

    /** 报告用例信息更改 */
    public int updateReportCase(ReportCase reportCase, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} reportCase {}", ip, JSON.toJSONString(reportCase));

        if(reportCase == null || reportCase.getId() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        if(reportCaseDao.updateReportCase(reportCase) == 1){
            return ResCode.C0;
        }
        messageMap.put("errmsg", "更改报告用例信息失败");
        return ResCode.C1008;
    }

    /** 通过报告用例id报告用例信息删除 */
    public int deleteByReportId(Integer[] reportBasicIds, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} reportBasicIds {}", ip, reportBasicIds);
        for(Integer reportBasicId: reportBasicIds){
            List<ReportCaseDto> reportCaseDtos = reportCaseDao.getByReportId(reportBasicId);
            if(reportCaseDtos == null || reportCaseDtos.size() == 0){
                return ResCode.C1008;
            }
            for(ReportCaseDto reportCaseDto: reportCaseDtos){
                int code = reportInterfacService.deleteByReportCaseId(reportCaseDto.getId(), messageMap, request);
                if(code != ResCode.C0){
                    return code;
                }
            }
            int actionNum = reportCaseDao.deleteByReportId(reportBasicId);
            if(actionNum == 0){
                messageMap.put("errmsg", "删除报告用例失败");
                return ResCode.C1008;
            }
        }

        return ResCode.C0;
    }
}
