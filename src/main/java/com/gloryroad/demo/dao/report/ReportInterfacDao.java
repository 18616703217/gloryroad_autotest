package com.gloryroad.demo.dao.report;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.entity.cases.CasesInterfac;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;
import com.gloryroad.demo.entity.report.ReportInterfac;
import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;
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
public class ReportInterfacDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询接口信息通过用例id
    public List<ReportInterfac> getByReportCaseId(Integer reportCaseId){
        String sql = String.format("SELECT * FROM report_interfac where report_case_id = %s and status = 0;", reportCaseId);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<ReportInterfac> reportInterfacs = mappingObject(list);
        return reportInterfacs;
    }

    // 插入接口断言信息
    public int insertReportInterfacs(List<ReportInterfac> reportInterfacs) throws SQLException {

        String sql = "insert into report_interfac(report_case_id, cases_interfac_id, interfac_name, step_num, url, remark, " +
                "method_type, query_Datas, forms, headers, jsons, asserts, run_status) " +
                "values(%s,%s,'%s',%s,'%s','%s','%s','%s','%s','%s','%s','%s','%s')";
        int actionNum = 0;
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        boolean ac = conn.getAutoCommit();
        conn.setAutoCommit(false);
        for(ReportInterfac reportInterfac: reportInterfacs) {
            sql = String.format(sql, reportInterfac.getReportCaseId(), reportInterfac.getCasesInterfacId(),
                    reportInterfac.getInterfacName(), reportInterfac.getStepNum(), reportInterfac.getUrl(),
                    reportInterfac.getRemark(), reportInterfac.getMethodType().getValue(), reportInterfac.getQueryDatas(),
                    reportInterfac.getForms(),reportInterfac.getHeaders(),reportInterfac.getJsons(),
                    reportInterfac.getAsserts(), reportInterfac.getRunState().getValue());

            System.out.println(sql);
            actionNum += jdbcTemplate.update(sql);
        }
        if(actionNum != reportInterfacs.size()){
            conn.rollback();
        }else {
            conn.commit();
        }
        conn.setAutoCommit(ac);
        return actionNum;
    }

    // 更改接口断言信息
    public int updateReportInterfacs(List<ReportInterfac> reportInterfacs) throws SQLException {
        int actionNum = 0;
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        boolean ac = conn.getAutoCommit();
        conn.setAutoCommit(false);

        for(ReportInterfac reportInterfac: reportInterfacs) {
            if(reportInterfac.getRunState() != null){
                String sql = "update report_interfac set run_status=" + reportInterfac.getRunState().getValue();
                sql += " where id = " + reportInterfac.getId() + ";";
                System.out.println(sql);
                actionNum += jdbcTemplate.update(sql);
            }
        }
        if(actionNum != reportInterfacs.size()){
            conn.rollback();
        }else {
            conn.commit();
        }
        conn.setAutoCommit(ac);
        return actionNum;
    }

    // 删除接口信息通过用例ID
    public int deleteByReportCaseId(Integer reportCaseId){
        String sql = String.format("update report_interfac where report_case_id = %s and status = 0;", reportCaseId);
        int actionNum = jdbcTemplate.update(sql);
        System.out.println(sql);
        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<ReportInterfac> mappingObject(List<Map<String,Object>> list){
        List<ReportInterfac> reportInterfacs = Lists.newArrayList();
        for(Map<String,Object> map: list){
            ReportInterfac reportInterfac = new ReportInterfac();
            reportInterfac.setId((Integer) map.get("id"));
            reportInterfac.setReportCaseId((Integer) map.get("report_case_id"));
            reportInterfac.setCasesInterfacId((Integer) map.get("cases_interfac_id"));
            reportInterfac.setInterfacName((String) map.get("interfac_name"));
            reportInterfac.setUrl((String) map.get("url"));
            reportInterfac.setStepNum((Integer) map.get("step_num"));
            reportInterfac.setMethodType(GloryRoadEnum.CaseSubMethod.getCaseSubMethod((String) map.get("method_type")));
            reportInterfac.setForms((JSONObject) map.get("forms"));
            reportInterfac.setRunState(GloryRoadEnum.RunStatus.getRunStatus((String) map.get("run_status")));
            reportInterfac.setQueryDatas((JSONObject) map.get("query_datas"));
            reportInterfac.setJsons((JSONObject) map.get("json"));
            reportInterfac.setHeaders((JSONObject) map.get("headers"));
            reportInterfac.setAsserts((String) map.get("cases_interfac_asserts"));
            reportInterfac.setStatus((Integer) map.get("status"));
            reportInterfac.setRemark((String) map.get("report"));
            reportInterfacs.add(reportInterfac);
            System.out.println("reportInterfac = " + reportInterfac);
        }
        return reportInterfacs;
    }
}
