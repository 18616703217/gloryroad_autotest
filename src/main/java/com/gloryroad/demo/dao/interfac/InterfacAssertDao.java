package com.gloryroad.demo.dao.interfac;

import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.entity.interfac.InterfacAssert;
import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class InterfacAssertDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询接口断言信息通过id
    public List<InterfacAssert> getInterfacAssertsByInterfacId(Integer interfacId){
        String sql = String.format("SELECT * FROM interfac_assert where interfac_id = %s and status = 0;", interfacId);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<InterfacAssert> interfacBasicList = mappingObject(list);

        return interfacBasicList;
    }

    // 插入接口断言信息
    public int insertInterfacAssert(InterfacAssert interfacAssert){

        String sql = "insert into interfac_assert(interfac_id, assert_position, assert_content, create_account, createTime) " +
                "values(%s,'%s','%s','%s',%s)";

        sql = String.format(sql, interfacAssert.getInterfacId(), interfacAssert.getAssertPosition().getValue(),
                interfacAssert.getAssertContent(), interfacAssert.getCreateAccount(), interfacAssert.getCreateTime());

        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);
        return actionNum;
    }

    // 更改接口断言信息
    public int updateInterfacAssert(InterfacAssert interfacAssert){

        String sql = "update interfac_basic set createTime=" + interfacAssert.getCreateTime();
        if (interfacAssert.getAssertPosition() != null){
            sql += ",assert_position='" + interfacAssert.getAssertPosition().getValue() + "'";
        }
        if (interfacAssert.getAssertContent() != null){
            sql += ",assert_content='" + interfacAssert.getAssertContent() + "'";
        }
        sql += " where id = " + interfacAssert.getId() + ";";
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);
        return actionNum;
    }

    // 删除接口断言信息
    public int deleteInterfacAsserts(Integer[] ids){

        String sql = "update interfac_assert set status=1 where id in (%s);";
        sql = String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 通过接口ID删除
    public int deleteByInterfacId(Integer[] interfacIds){

        String sql = "update interfac_assert set status=1 where interfac_id in (%s);";
        sql = String.format(sql, Joiner.on(',').join(interfacIds));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<InterfacAssert> mappingObject(List<Map<String,Object>> list){
        List<InterfacAssert> interfacBasicList = Lists.newArrayList();
        for(Map<String,Object> map: list){
            InterfacAssert interfacAssert = new InterfacAssert();
            interfacAssert.setId((Integer) map.get("id"));
            interfacAssert.setInterfacId((Integer) map.get("interfac_id"));
            interfacAssert.setAssertPosition(GloryRoadEnum.AssertPosition.getAssertPosition((String) map.get("assert_position")));
            interfacAssert.setAssertContent((String) map.get("assert_content"));
            interfacAssert.setStatus((Integer) map.get("status"));
            interfacAssert.setCreateTime((Integer) map.get("createTime"));
            interfacAssert.setCreateAccount((String) map.get("create_account"));
            interfacBasicList.add(interfacAssert);
            System.out.println("interfacAssert = " + interfacAssert);
        }
        return interfacBasicList;
    }
}
