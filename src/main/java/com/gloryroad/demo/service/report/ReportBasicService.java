package com.gloryroad.demo.service.report;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.cases.CasesBasicQueryVo;
import com.gloryroad.demo.Vo.report.ReportBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.cases.CasesBasicDao;
import com.gloryroad.demo.dao.report.ReportBasicDao;
import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.dto.report.ReportBasicDto;
import com.gloryroad.demo.dto.report.ReportCaseDto;
import com.gloryroad.demo.entity.cases.CasesBasic;
import com.gloryroad.demo.entity.report.ReportBasic;
import com.gloryroad.demo.service.cases.CasesInterfacService;
import com.gloryroad.demo.service.system.SystemGroupService;
import com.gloryroad.demo.utils.IpUtil;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class ReportBasicService {

    @Autowired
    private ReportBasicDao reportBasicDao;
    
    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private ReportCaseService reportCaseService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportBasicService.class);

    /** 报告信息查找 */
    public PageModel<ReportBasicDto> findReportBasic(ReportBasicQueryVo reportBasicQueryVo, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} reportBasicQueryVo {}", ip, JSON.toJSONString(reportBasicQueryVo));
        List<ReportBasicDto> reportBasicDtos;
        PageModel<ReportBasicDto> page = new PageModel();
        if(reportBasicQueryVo.getId() != null){
            reportBasicDtos = reportBasicDao.getReportBasicById(reportBasicQueryVo.getId());
        }else {
            reportBasicDtos = reportBasicDao.getReportBasics(reportBasicQueryVo);
        }

        for(ReportBasicDto reportBasicDto: reportBasicDtos){
            reportBasicDto.setGroupName(systemGroupService.findSystemGroupById(reportBasicDto.getGroupId()).getGroupName());
            reportBasicDto.setReportCaseDtos(reportCaseService.findByReportId(reportBasicDto.getId(), request));
        }

        page.setResult(reportBasicDtos);
        return page;
    }

    /** 报告信息插入 */
    public int insertReportBasic(ReportBasicDto reportBasicDto, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} reportBasicDto {}", ip, JSON.toJSONString(reportBasicDto));

        if(reportBasicDto == null
                || reportBasicDto.getReportName() == null
                || reportBasicDto.getGroupId() == null
                || reportBasicDto.getCreateAccount() == null)
        {
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        Integer reportBasicId = reportBasicDao.insertReportBasic(reportBasicDto);
        if(reportBasicId == null || reportBasicId == 0){
            messageMap.put("errmsg", "插入报告失败");
            return ResCode.C1008;
        };

        for(ReportCaseDto reportCaseDto: reportBasicDto.getReportCaseDtos()){
            reportCaseDto.setReportBaseId(reportBasicId);
        }

        reportCaseService.insertReportCasess(reportBasicDto.getReportCaseDtos(), messageMap, request);
        messageMap.put("errmsg", "插入报告失败");
        return ResCode.C1008;
    }

    /** 报告信息更改 */
    public int updateReportBasic(ReportBasicDto reportBasicDto, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} reportBasicDto {}", ip, JSON.toJSONString(reportBasicDto));

        if(reportBasicDto == null || reportBasicDto.getId() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        reportBasicDto.setCreateTime(System.currentTimeMillis());
        if(reportBasicDao.updateReportBasic(reportBasicDto) == 1){
            return ResCode.C0;
        }
        messageMap.put("errmsg", "更改报告信息失败");
        return ResCode.C1008;
    }

    /** 报告信息删除 */
    public int deleteReportBasics(Integer[] ids, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("delete ip {} ids {}", ip, JSON.toJSONString(ids));

        if(ids==null || ids.length==0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        for(Integer id: ids){
            List<ReportBasicDto> reportBasicDtos = reportBasicDao.getReportBasicById(id);
            if(reportBasicDtos.size() == 0
                    || GloryRoadEnum.TaskStatus.EXECUTING == reportBasicDtos.get(0).getTaskStatus()
                    || GloryRoadEnum.TaskStatus.PREPARE == reportBasicDtos.get(0).getTaskStatus()
                    || GloryRoadEnum.TaskStatus.TO_BE_EXEC == reportBasicDtos.get(0).getTaskStatus() ){
                messageMap.put("errmsg", "报告状态不是终态，不可删除");
                return ResCode.C1008;

            }
        }

        int code = reportCaseService.deleteByReportId(ids, messageMap, request);
        if(code != ResCode.C0){
            return code;
        }

        if(reportBasicDao.deleteReportBasics(ids) == 0){
            messageMap.put("errmsg", "删除报告信息失败");
            return ResCode.C1008;
        }

        return ResCode.C0;
    }
}
