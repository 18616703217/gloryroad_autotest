package com.gloryroad.demo.dao.cases;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.Vo.cases.CasesBasicQueryVo;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.cases.CasesBasic;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
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
public class CasesBasicDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询用例信息通过id
    public List<CasesBasicDto> getCasesBasicById(String id){
        String sql = String.format("SELECT * FROM cases_basic where id = %s and status = 0;", id);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<CasesBasicDto> casesBasicDtos = mappingObject(list);
        return casesBasicDtos;
    }

    // 查询用例信息通过条件
    public List<CasesBasicDto> getCasesBasics(CasesBasicQueryVo casesBasicQueryVo){
        int pageNo = casesBasicQueryVo.getPageNo();
        int pageSize = casesBasicQueryVo.getPageSize();

        String sql = "SELECT * FROM cases_basic where status = 0";
        if (casesBasicQueryVo.getCaseName() != null){
            sql += " and case_name like '"+ casesBasicQueryVo.getCaseName() +"%'";
        }
        if (casesBasicQueryVo.getCreateAccount() != null){
            sql += " and create_account like '"+ casesBasicQueryVo.getCreateAccount() +"%'";
        }
        if (casesBasicQueryVo.getGroupId() != null){
            sql += " and group_id = "+ casesBasicQueryVo.getGroupId();
        }

        sql += String.format(" order by %s limit %s,%s;", casesBasicQueryVo.getSort(),  (pageNo-1) * pageNo, pageSize);
        System.out.println("sql = " + sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<CasesBasicDto> casesBasicDtos = mappingObject(list);
        return casesBasicDtos;
    }

    // 插入用例信息
    public int insertCasesBasic(CasesBasic casesBasic){

        String sql = "insert into interfac_basic(case_name, remark, group_id, create_account, createTime) " +
                "values('%s','%s',%s, '%s', %s)";

        String finalSql = String.format(sql, casesBasic.getCaseName(), casesBasic.getRemark(),
                casesBasic.getGroupId(), casesBasic.getCreateAccount(), casesBasic.getCreateTime());

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
    public int updateCasesBasic(CasesBasic casesBasic){

        String sql = "update cases_basic set createtime=" + casesBasic.getCreateTime();
        if (casesBasic.getCaseName() != null){
            sql += ",case_name='" + casesBasic.getCaseName() + "'";
        }
        if (casesBasic.getRemark() != null){
            sql += ",remark='" + casesBasic.getRemark() + "'";
        }
        if (casesBasic.getGroupId() != null){
            sql += ",group_id=" + casesBasic.getGroupId();
        }
        sql += " where id = " + casesBasic.getId() + ";";
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 删除用例信息
    public int deleteCasesBasics(Integer[] ids){

        String sql = "update cases_basic set status=1 where id in (%s);";
        String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<CasesBasicDto> mappingObject(List<Map<String,Object>> list){
        List<CasesBasicDto> casesBasicDtos = new LinkedList<>();
        for(Map<String,Object> map: list){
            CasesBasicDto casesBasicDto = new CasesBasicDto();
            casesBasicDto.setId((Integer) map.get("id"));
            casesBasicDto.setCaseName((String) map.get("case_name"));
            casesBasicDto.setRemark((String) map.get("remark"));
            casesBasicDto.setGroupId((Integer) map.get("group_id"));
            casesBasicDto.setCreateAccount((String) map.get("create_account"));
            casesBasicDto.setCreateTime((long) map.get("createTime"));
            casesBasicDto.setStatus((Integer) map.get("status"));
            casesBasicDtos.add(casesBasicDto);
            System.out.println("casesBasicDto = " + casesBasicDto);
        }
        return casesBasicDtos;
    }
}
