package com.gloryroad.demo.dao.report;

import com.gloryroad.demo.Vo.cases.CasesBasicQueryVo;
import com.gloryroad.demo.Vo.report.ReportBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.dto.report.ReportBasicDto;
import com.gloryroad.demo.entity.cases.CasesBasic;
import com.gloryroad.demo.entity.report.ReportBasic;
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
public class ReportBasicDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询用例信息通过id
    public List<ReportBasicDto> getReportBasicById(Integer id){
        String sql = String.format("SELECT * FROM report_basic where id = %s and status = 0;", id);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<ReportBasicDto> reportBasicDtos = mappingObject(list);
        return reportBasicDtos;
    }

    // 查询用例信息通过条件
    public List<ReportBasicDto> getReportBasics(ReportBasicQueryVo reportBasicQueryVo){
        int pageNo = reportBasicQueryVo.getPageNo();
        int pageSize = reportBasicQueryVo.getPageSize();

        String sql = "SELECT * FROM report_basic where status = 0";
        if (reportBasicQueryVo.getReportName() != null){
            sql += " and report_name like '"+ reportBasicQueryVo.getReportName() +"%'";
        }
        if (reportBasicQueryVo.getCreateAccount() != null){
            sql += " and create_account like '"+ reportBasicQueryVo.getCreateAccount() +"%'";
        }
        if (reportBasicQueryVo.getGroupId() != null){
            sql += " and group_id = "+ reportBasicQueryVo.getGroupId();
        }

        sql += String.format(" order by %s limit %s,%s;", reportBasicQueryVo.getSort(),  (pageNo-1) * pageNo, pageSize);
        System.out.println("sql = " + sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<ReportBasicDto> reportBasicDtos = mappingObject(list);
        return reportBasicDtos;
    }

    // 插入用例信息
    public int insertReportBasic(ReportBasic reportBasic){

        String sql = "insert into report_basic(report_name, task_id, group_id, create_account, execution_env, " +
                "start_time, task_status, createTime) " +
                "values('%s',%s,%s,'%s','%s',%s, '%s', %s)";

        String finalSql = String.format(sql, reportBasic.getReportName(), reportBasic.getTaskId(),
                reportBasic.getGroupId(), reportBasic.getCreateAccount(), reportBasic.getExecutionEnv(),
                reportBasic.getStartTime(), GloryRoadEnum.TaskStatus.PREPARE.getValue(), reportBasic.getCreateTime());

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

    // 更改用例信息
    public int updateReportBasic(ReportBasic reportBasic){

        String sql = "update report_basic set createtime=" + reportBasic.getCreateTime();
        if (reportBasic.getReportName() != null){
            sql += ",report_name='" + reportBasic.getReportName() + "'";
        }
        if (reportBasic.getEndTime() != 0){
            sql += ",end_time=" + reportBasic.getEndTime();
        }
        if (reportBasic.getTaskStatus() != null){
            sql += ",task_status='" + reportBasic.getTaskStatus() + "'";
        }
        sql += " where id = " + reportBasic.getId() + ";";
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 删除用例信息
    public int deleteReportBasics(Integer[] ids){

        String sql = "update report_basic set status=1 where id in (%s);";
        String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<ReportBasicDto> mappingObject(List<Map<String,Object>> list){
        List<ReportBasicDto> reportBasicDtos = new LinkedList<>();
        for(Map<String,Object> map: list){
            ReportBasicDto reportBasicDto = new ReportBasicDto();
            reportBasicDto.setId((Integer) map.get("id"));
            reportBasicDto.setReportName((String) map.get("case_name"));
            reportBasicDto.setTaskId((String) map.get("task_id"));
            reportBasicDto.setGroupId((Integer) map.get("group_id"));
            reportBasicDto.setEndTime((long) map.get("end_time"));
            reportBasicDto.setErrMsg((String) map.get("err_msg"));
            reportBasicDto.setExecutionEnv((String) map.get("execution_env"));
            reportBasicDto.setTaskStatus(GloryRoadEnum.TaskStatus.getTaskStatus((String) map.get("task_status")));
            reportBasicDto.setStartTime((long) map.get("start_time"));
            reportBasicDto.setCreateAccount((String) map.get("create_account"));
            reportBasicDto.setCreateTime((long) map.get("createTime"));
            reportBasicDto.setStatus((Integer) map.get("status"));
            reportBasicDtos.add(reportBasicDto);
            System.out.println("casesBasicDto = " + reportBasicDto);
        }
        return reportBasicDtos;
    }
}
