package com.gloryroad.demo.dao.interfac;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        return interfacBasicList;
    }

    // 插入接口信息
    public int insertInterfacBasic(InterfacBasic interfacBasic){

        String sql = "insert into interfac_basic(interfac_name, remark, group_id, create_account, method_type, body_type" +
                "interfac_form_data, interfac_json_data, interfac_query_data, interfac_header_data, createTime) " +
                "values('%s','%s',%s,'%s','%s','%s','%s','%s','%s','%s',%s)";
        
        sql = String.format(sql, interfacBasic.getInterfacName(), interfacBasic.getRemark(),
                interfacBasic.getGroupId(), interfacBasic.getCreateAccount(), interfacBasic.getMethodType().getValue(),
                interfacBasic.getBodyType().getValue(), interfacBasic.getInterfacFormData().toJSONString(),
                interfacBasic.getInterfacJsonData().toJSONString(), interfacBasic.getInterfacQueryData().toJSONString(),
                interfacBasic.getInterfacHeaderData().toJSONString(), interfacBasic.getCreateTime());
        
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 更改接口信息
    public int updateInterfacBasic(InterfacBasic interfacBasic){

        String sql = "update interfac_basic set createtime=" + interfacBasic.getCreateTime();
        if (interfacBasic.getInterfacName() != null){
            sql += ",interfac_name='" + interfacBasic.getInterfacName() + "'";
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
        String.format(sql, Joiner.on(',').join(ids));
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
            interfacBasic.setGroupId((Integer) map.get("group_id"));
            interfacBasic.setCreateAccount((String) map.get("create_account"));
            interfacBasic.setMethodType(GloryRoadEnum.CaseSubMethod.getCaseSubMethod((String) map.get("method_type")));
            interfacBasic.setBodyType(GloryRoadEnum.CaseBodyType.getCaseBodyType((String) map.get("body_type")));
            interfacBasic.setInterfacFormData((JSONObject) map.get("interfac_form_data"));
            interfacBasic.setInterfacJsonData((JSONObject) map.get("interfac_json_data"));
            interfacBasic.setInterfacQueryData((JSONObject) map.get("interfac_query_data"));
            interfacBasic.setInterfacHeaderData((JSONObject) map.get("interfac_header_data"));
            interfacBasic.setCreateTime((long) map.get("createTime"));
            interfacBasic.setStatus((Integer) map.get("status"));
            interfacBasicList.add(interfacBasic);
            System.out.println("interfacBasic = " + interfacBasic);
        }
        return interfacBasicList;
    }
}
