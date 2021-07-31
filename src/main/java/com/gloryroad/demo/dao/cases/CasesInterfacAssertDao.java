package com.gloryroad.demo.dao.cases;

import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;
import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class CasesInterfacAssertDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询接口断言信息通过id
    public List<CasesInterfacAssert> getCasesInterfacAssertsByCasesInterfacId(Integer casesInterfacId){
        String sql = String.format("SELECT * FROM cases_interfac_assert where interfac_id = %s and status = 0;", casesInterfacId);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<CasesInterfacAssert> casesInterfacAsserts = mappingObject(list);
        return casesInterfacAsserts;
    }

    // 插入接口断言信息
    public int insertCasesInterfacAsserts(List<CasesInterfacAssert> casesInterfacAsserts) throws SQLException {

        String sql = "insert into cases_interfac_assert(interfac_id, assert_position, assert_content, create_account, createTime) " +
                "values(%s,'%s','%s','%s',%s)";
        int actionNum = 0;
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        boolean ac = conn.getAutoCommit();
        conn.setAutoCommit(false);
        for(CasesInterfacAssert casesInterfacAssert: casesInterfacAsserts) {
            sql = String.format(sql, casesInterfacAssert.getInterfacId(), casesInterfacAssert.getAssertPosition().getValue(),
                    casesInterfacAssert.getAssertContent(), casesInterfacAssert.getCreateAccount(), casesInterfacAssert.getCreateTime());

            System.out.println(sql);
            actionNum += jdbcTemplate.update(sql);
        }
        if(actionNum != casesInterfacAsserts.size()){
            conn.rollback();
        }else {
            conn.commit();
        }
        conn.setAutoCommit(ac);
        return actionNum;
    }

    // 更改接口断言信息
    public int updateCasesInterfacAsserts(List<CasesInterfacAssert> casesInterfacAsserts) throws SQLException {
        int actionNum = 0;
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        boolean ac = conn.getAutoCommit();
        conn.setAutoCommit(false);

        for(CasesInterfacAssert casesInterfacAssert: casesInterfacAsserts) {
            String sql = "update cases_interfac_assert set createtime=" + casesInterfacAssert.getCreateTime();
            if (casesInterfacAssert.getAssertPosition() != null) {
                sql += ",assert_position='" + casesInterfacAssert.getAssertPosition().getValue() + "'";
            }
            if (casesInterfacAssert.getAssertContent() != null) {
                sql += ",assert_content='" + casesInterfacAssert.getAssertContent() + "'";
            }
            sql += " where id = " + casesInterfacAssert.getId() + ";";
            System.out.println(sql);
            actionNum += jdbcTemplate.update(sql);
        }
        if(actionNum != casesInterfacAsserts.size()){
            conn.rollback();
        }else {
            conn.commit();
        }
        conn.setAutoCommit(ac);
        return actionNum;
    }

    // 删除接口断言信息
    public int deleteCasesInterfacAsserts(Integer[] ids){

        String sql = "update cases_interfac_assert set status=1 where id in (%s);";
        sql = String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 删除接口断言信息通过id
    public int deleteByCasesInterfacId(Integer casesInterfacId){
        String sql = String.format("update cases_interfac_assert set status = 1 where interfac_id = %s;", casesInterfacId);
        int actionNum = jdbcTemplate.update(sql);
        System.out.println(sql);
        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<CasesInterfacAssert> mappingObject(List<Map<String,Object>> list){
        List<CasesInterfacAssert> casesInterfacAsserts = Lists.newArrayList();
        for(Map<String,Object> map: list){
            CasesInterfacAssert casesInterfacAssert = new CasesInterfacAssert();
            casesInterfacAssert.setId((Integer) map.get("id"));
            casesInterfacAssert.setInterfacId((Integer) map.get("interfac_id"));
            casesInterfacAssert.setAssertPosition(GloryRoadEnum.AssertPosition.getAssertPosition((String) map.get("assert_position")));
            casesInterfacAssert.setAssertContent((String) map.get("assert_content"));
            casesInterfacAssert.setStatus((Integer) map.get("status"));
            casesInterfacAssert.setCreateTime((Integer) map.get("createTime"));
            casesInterfacAssert.setCreateAccount((String) map.get("create_account"));
            casesInterfacAsserts.add(casesInterfacAssert);
            System.out.println("CasesInterfacAssert = " + casesInterfacAssert);
        }
        return casesInterfacAsserts;
    }
}
