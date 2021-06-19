package com.gloryroad.demo.dao.report;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.dto.report.ReportCaseDto;
import com.gloryroad.demo.entity.cases.CasesInterfac;
import com.gloryroad.demo.entity.report.ReportCase;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ReportCaseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询报告用例信息通过报告id
    public List<ReportCaseDto> getByReportId(Integer reportId){
        String sql = String.format("SELECT * FROM report_case where report_id = %s and status = 0;", reportId);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<ReportCaseDto> reportCaseDtos = mappingObject(list);
        return reportCaseDtos;
    }

    // 插入报告用例信息
    public int insertReportCase(ReportCaseDto reportCaseDto) {

        String sql = "insert into report_cases(case_id, report_base_id, case_name, case_remark, run_status) " +
                "values(%s, %s, '%s','%s','%s')";
        String finalSql = String.format(sql, reportCaseDto.getCaseId(), reportCaseDto.getReportBaseId(),
                reportCaseDto.getCaseName(), reportCaseDto.getCaseRemark(), reportCaseDto.getRunState().getValue());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException {

                return conn.prepareStatement(finalSql);

            }
        },keyHolder);
        int i = Objects.requireNonNull(keyHolder.getKey()).intValue();//插入的数据的主键

        return i;
    }

    // 更改报告用例信息
    public int updateReportCase(ReportCase reportCase){
        int actionNum = 0;
        String sql = "update report_cases set ";
        if (reportCase.getRunState() != null){
            sql += ",run_status='" + reportCase.getRunState().getValue() + "'";
            sql += " where id = " + reportCase.getId() + ";";
            System.out.println(sql);
            actionNum = jdbcTemplate.update(sql);
        }
        return actionNum;
    }

    // 删除报告用例信息
    public int deleteReportCases(Integer[] ids){

        String sql = "update report_cases set status=1 where id in (%s);";
        String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 删除报告用例信息通过用例id
    public int deleteByReportId(Integer reportId){
        String sql = String.format("update report_cases set status=1 where report_id = %s and status = 0;", reportId);
        int actionNum = jdbcTemplate.update(sql);
        System.out.println(sql);
        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<ReportCaseDto> mappingObject(List<Map<String,Object>> list){
        List<ReportCaseDto> reportCaseDtos = new LinkedList<>();
        for(Map<String,Object> map: list){
            ReportCaseDto reportCaseDto = new ReportCaseDto();
            reportCaseDto.setId((Integer) map.get("id"));
            reportCaseDto.setCaseName((String) map.get("case_name"));
            reportCaseDto.setCaseId((Integer) map.get("case_id"));
            reportCaseDto.setReportBaseId((Integer) map.get("report_name"));
            reportCaseDto.setCaseRemark((String) map.get("case_remark"));
            reportCaseDto.setStatus((Integer) map.get("status"));
            reportCaseDto.setRunState(GloryRoadEnum.RunStatus.getRunStatus((String) map.get("run_status")));
            reportCaseDtos.add(reportCaseDto);
            System.out.println("interfacBasic = " + reportCaseDto);
        }
        return reportCaseDtos;
    }
}
