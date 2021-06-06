package com.gloryroad.demo.service.report;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.cases.CasesInterfacAssertDao;
import com.gloryroad.demo.dao.report.ReportInterfacDao;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;
import com.gloryroad.demo.entity.report.ReportInterfac;
import com.gloryroad.demo.utils.IpUtil;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ReportInterfacService {
    
    @Autowired
    private ReportInterfacDao reportInterfacDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportInterfacService.class);

    /** 报告详情信息查找 */
    public List<ReportInterfac> findByReportCaseId(Integer reportCaseId, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} reportCaseId {}", ip, reportCaseId);
        List<ReportInterfac> reportInterfacs = Lists.newArrayList();
        if(reportCaseId == null){
            return reportInterfacs;
        }
        reportInterfacs = reportInterfacDao.getByReportCaseId(reportCaseId);
        return reportInterfacs;
    }

    /** 报告详情信息插入 */
    public int insertReportInterfacs(List<ReportInterfac> reportInterfacs, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} reportInterfacs {}", ip, JSON.toJSONString(reportInterfacs));

        if(reportInterfacs == null
                || reportInterfacs.size() == 0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        for(ReportInterfac reportInterfac: reportInterfacs) {
            if(reportInterfac.getId() != null){
                messageMap.put("errmsg", "参数有误，主键id非空");
                return ResCode.C1002;
            }
            reportInterfac.setRunState(GloryRoadEnum.RunStatus.TO_BE_EXEC);
        }
        try {
            if(reportInterfacDao.insertReportInterfacs(reportInterfacs) == reportInterfacs.size()){
                return ResCode.C0;
            }
        }catch (SQLException e){
            LOGGER.error("insertCasesInterfacAsserts 失败 {}", e.toString());
        }
        messageMap.put("errmsg", "插入报告详情信息失败");
        return ResCode.C1008;
    }

    /** 报告详情信息更改 */
    public int updateReportInterfacs(List<ReportInterfac> reportInterfacs, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} reportInterfacs {}", ip, JSON.toJSONString(reportInterfacs));

        if(reportInterfacs == null || reportInterfacs.size() == 0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        try {
            if(reportInterfacDao.updateReportInterfacs(reportInterfacs) == reportInterfacs.size()){
                return ResCode.C0;
            }
        }catch (SQLException e){
            LOGGER.error("updateReportInterfacs 失败 {}", e.toString());
        }

        messageMap.put("errmsg", "更改报告详情信息失败");
        return ResCode.C1008;
    }

    /** 报告详情信息删除 */
    public int deleteByReportCaseId(Integer reportCaseId, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("deleteByReportCaseId ip {} reportCaseId {}", ip, JSON.toJSONString(reportCaseId));

        if(reportCaseId==null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        if(reportInterfacDao.deleteByReportCaseId(reportCaseId) > 0){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "删除报告详情信息失败");
        return ResCode.C1008;
    }
}
