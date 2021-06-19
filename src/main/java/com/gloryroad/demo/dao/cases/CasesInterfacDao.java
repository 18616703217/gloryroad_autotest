package com.gloryroad.demo.dao.cases;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.cases.CasesInterfac;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.gloryroad.demo.utils.TimesUtil;
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
public class CasesInterfacDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询接口信息通过用例id
    public List<CasesInterfacDto> getCasesInterfacByCaeseId(Integer casesId){
        String sql = String.format("SELECT * FROM cases_interfac where cases_id = %s and status = 0;", casesId);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<CasesInterfacDto> casesInterfacDtos = mappingObject(list);
        return casesInterfacDtos;
    }

    // 插入接口信息
    public int insertInterfacBasic(CasesInterfacDto casesInterfac) {

        String sql = "insert into cases_interfac(iterfac_name, remark, step_num, cases_id, create_account, method_type, body_type," +
                "interfac_form_data, interfac_json_data, interfac_query_data, interfac_header_data, url, createTime) " +
                "values('%s','%s', %s, %s, %s,'%s','%s','%s','%s','%s','%s','%s','%s',%s)";
        String finalSql = String.format(sql, casesInterfac.getInterfacName(), casesInterfac.getRemark(),
                casesInterfac.getStepNum(), casesInterfac.getCasesId(),
                casesInterfac.getCreateAccount(), casesInterfac.getMethodType().getValue(),
                casesInterfac.getBodyType().getValue(), casesInterfac.getInterfacFormData().toJSONString(),
                casesInterfac.getInterfacJsonData().toJSONString(), casesInterfac.getInterfacQueryData().toJSONString(),
                casesInterfac.getInterfacHeaderData().toJSONString(), casesInterfac.getCreateTime());

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

    // 更改接口信息
    public int updateCasesInterfacBasic(CasesInterfac casesInterfac){

        String sql = "update cases_interfac set createtime=" + casesInterfac.getCreateTime();
        if (casesInterfac.getInterfacFormData() != null){
            sql += ",interfac_form_data='" + casesInterfac.getInterfacFormData().toJSONString() + "'";
        }
        if (casesInterfac.getUrl() != null){
            sql += ",url='" + casesInterfac.getUrl() + "'";
        }
        if (casesInterfac.getInterfacJsonData() != null){
            sql += ",interfac_json_data='" + casesInterfac.getInterfacJsonData().toJSONString() + "'";
        }
        if (casesInterfac.getInterfacQueryData() != null){
            sql += ",interfac_query_data='" + casesInterfac.getInterfacQueryData().toJSONString() + "'";
        }
        if (casesInterfac.getStepNum() != null){
            sql += ",step_num=" + casesInterfac.getStepNum();
        }
        if (casesInterfac.getInterfacHeaderData() != null){
            sql += ",interfac_header_data='" + casesInterfac.getInterfacHeaderData().toJSONString() + "'";
        }
        if (casesInterfac.getBodyType() != null){
            sql += ",body_type='" + casesInterfac.getBodyType().getValue() + "'";
        }
        sql += " where id = " + casesInterfac.getId() + ";";
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 删除接口信息
    public int deleteCasesInterfacs(Integer[] ids){

        String sql = "update cases_interfac set status=1 where id in (%s);";
        String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 查询接口信息通过用例id
    public int deleteCasesInterfacByCaeseId(Integer casesId){
        String sql = String.format("update cases_interfac set status=1 where cases_id = %s and status = 0;", casesId);
        int actionNum = jdbcTemplate.update(sql);
        System.out.println(sql);
        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<CasesInterfacDto> mappingObject(List<Map<String,Object>> list){
        List<CasesInterfacDto> casesInterfacDtos = new LinkedList<>();
        for(Map<String,Object> map: list){
            CasesInterfacDto casesInterfacDto = new CasesInterfacDto();
            casesInterfacDto.setId((Integer) map.get("id"));
            casesInterfacDto.setCasesId((Integer) map.get("cases_id"));
            casesInterfacDto.setInterfacName((String) map.get("interfac_name"));
            casesInterfacDto.setRemark((String) map.get("remark"));
            casesInterfacDto.setUrl((String) map.get("url"));
            casesInterfacDto.setStepNum((Integer) map.get("step_num"));
            casesInterfacDto.setCreateAccount((String) map.get("create_account"));
            casesInterfacDto.setMethodType(GloryRoadEnum.CaseSubMethod.getCaseSubMethod((String) map.get("method_type")));
            casesInterfacDto.setBodyType(GloryRoadEnum.CaseBodyType.getCaseBodyType((String) map.get("body_type")));
            casesInterfacDto.setInterfacFormData((JSONObject) map.get("interfac_form_data"));
            casesInterfacDto.setInterfacJsonData((JSONObject) map.get("interfac_json_data"));
            casesInterfacDto.setInterfacQueryData((JSONObject) map.get("interfac_query_data"));
            casesInterfacDto.setInterfacHeaderData((JSONObject) map.get("interfac_header_data"));
            casesInterfacDto.setCreateTime((Integer) map.get("createTime"));
            casesInterfacDto.setStatus((Integer) map.get("status"));
            casesInterfacDtos.add(casesInterfacDto);
            System.out.println("interfacBasic = " + casesInterfacDto);
        }
        return casesInterfacDtos;
    }
}
