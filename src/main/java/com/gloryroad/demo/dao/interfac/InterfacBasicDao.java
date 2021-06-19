package com.gloryroad.demo.dao.interfac;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
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
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class InterfacBasicDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询接口信息通过id
    public List<InterfacBasicDto> getInterfacBasicById(Integer id){
        String sql = String.format("SELECT * FROM interfac_basic where id = %s and status = 0;", id);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<InterfacBasicDto> interfacBasicList = mappingObject(list);
        return interfacBasicList;
    }

    // 查询接口信息通过条件
    public List<InterfacBasicDto> getInterfacBasics(InterfacBasicQueryVo interfacBasicQueryVo){
        int pageNo = interfacBasicQueryVo.getPageNo();
        int pageSize = interfacBasicQueryVo.getPageSize();

        String sql = "SELECT * FROM interfac_basic where status = 0";
        if (interfacBasicQueryVo.getInterfacName() != null){
            sql += " and interfac_name like '"+ interfacBasicQueryVo.getInterfacName() +"%'";
        }
        if (interfacBasicQueryVo.getCreateAccount() != null){
            sql += " and create_account like '"+ interfacBasicQueryVo.getCreateAccount() +"%'";
        }
        if (interfacBasicQueryVo.getGroupId() != null){
            sql += " and group_id = "+ interfacBasicQueryVo.getGroupId();
        }

        sql += String.format(" order by %s limit %s,%s;", interfacBasicQueryVo.getSort(),  (pageNo-1) * pageNo, pageSize);
        System.out.println("sql = " + sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        List<InterfacBasicDto> interfacBasicList = mappingObject(list);
        System.out.println("interfacBasicList = " + interfacBasicList);
        return interfacBasicList;
    }

    // 插入接口信息
    public Integer insertInterfacBasic(InterfacBasic interfacBasic){

        String sql = "insert into interfac_basic(interfac_name, remark, group_id, create_account, method_type, body_type, " +
                "interfac_form_data, interfac_json_data, interfac_query_data, interfac_header_data, createTime, url) " +
                "values('%s','%s',%s,'%s','%s','%s','%s','%s','%s','%s',%s,'%s')";
        String finalSql = String.format(sql, interfacBasic.getInterfacName(), interfacBasic.getRemark(),
                interfacBasic.getGroupId(), interfacBasic.getCreateAccount(), interfacBasic.getMethodType().getValue(),
                interfacBasic.getBodyType().getValue(), interfacBasic.getInterfacFormData().toJSONString(),
                interfacBasic.getInterfacJsonData().toJSONString(), interfacBasic.getInterfacQueryData().toJSONString(),
                interfacBasic.getInterfacHeaderData().toJSONString(), interfacBasic.getCreateTime(), interfacBasic.getUrl());
        
        System.out.println(finalSql);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException {

                return conn.prepareStatement(finalSql, Statement.RETURN_GENERATED_KEYS);

            }
        },keyHolder);
        Integer i = Objects.requireNonNull(keyHolder.getKey()).intValue();//插入的数据的主键

        return i;
    }

    // 更改接口信息
    public int updateInterfacBasic(InterfacBasic interfacBasic){

        String sql = "update interfac_basic set createtime=" + interfacBasic.getCreateTime();
        if (interfacBasic.getInterfacName() != null){
            sql += ",interfac_name='" + interfacBasic.getInterfacName() + "'";
        }
        if (interfacBasic.getUrl() != null){
            sql += ",url='" + interfacBasic.getUrl() + "'";
        }
        if (interfacBasic.getRemark() != null){
            sql += ",remark='" + interfacBasic.getRemark() + "'";
        }
        if (interfacBasic.getInterfacFormData() != null){
            sql += ",interfac_form_data='" + interfacBasic.getInterfacFormData().toJSONString() + "'";
        }
        if (interfacBasic.getInterfacJsonData() != null){
            sql += ",interfac_json_data='" + interfacBasic.getInterfacJsonData().toJSONString() + "'";
        }
        if (interfacBasic.getInterfacQueryData() != null){
            sql += ",interfac_query_data='" + interfacBasic.getInterfacQueryData().toJSONString() + "'";
        }
        if (interfacBasic.getInterfacHeaderData() != null){
            sql += ",interfac_header_data='" + interfacBasic.getInterfacHeaderData().toJSONString() + "'";
        }
        if (interfacBasic.getMethodType() != null){
            sql += ",method_type='" + interfacBasic.getMethodType().getValue() + "'";
        }
        if (interfacBasic.getBodyType() != null){
            sql += ",body_type='" + interfacBasic.getBodyType().getValue() + "'";
        }
        if (interfacBasic.getGroupId() != null){
            sql += ",group_id=" + interfacBasic.getGroupId();
        }
        sql += " where id = " + interfacBasic.getId() + ";";
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 删除接口信息
    public int deleteInterfacBasics(Integer[] ids){

        String sql = "update interfac_basic set status=1 where id in (%s);";
        sql = String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<InterfacBasicDto> mappingObject(List<Map<String,Object>> list){
        List<InterfacBasicDto> interfacBasicList = new LinkedList<>();
        for(Map<String,Object> map: list){
            InterfacBasicDto interfacBasic = new InterfacBasicDto();
            interfacBasic.setId((Integer) map.get("id"));
            interfacBasic.setInterfacName((String) map.get("interfac_name"));
            interfacBasic.setRemark((String) map.get("remark"));
            interfacBasic.setUrl((String) map.get("url"));
            interfacBasic.setGroupId((Integer) map.get("group_id"));
            interfacBasic.setCreateAccount((String) map.get("create_account"));
            interfacBasic.setMethodType(GloryRoadEnum.CaseSubMethod.getCaseSubMethod((String) map.get("method_type")));
            interfacBasic.setBodyType(GloryRoadEnum.CaseBodyType.getCaseBodyType((String) map.get("body_type")));
            interfacBasic.setInterfacFormData(JSONObject.parseObject((String) map.get("interfac_form_data")));
            interfacBasic.setInterfacJsonData(JSONObject.parseObject((String) map.get("interfac_json_data")));
            interfacBasic.setInterfacQueryData(JSONObject.parseObject((String) map.get("interfac_query_data")));
            interfacBasic.setInterfacHeaderData(JSONObject.parseObject((String) map.get("interfac_header_data")));
            interfacBasic.setCreateDate(TimesUtil.timeStamp2Date((Integer) map.get("createTime"), null));
            interfacBasic.setCreateTime((Integer) map.get("createTime"));
            interfacBasic.setStatus((Integer) map.get("status"));

            interfacBasicList.add(interfacBasic);
        }
        return interfacBasicList;
    }
}
