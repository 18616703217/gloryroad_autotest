package com.gloryroad.demo.dao.system;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.system.SystemGroupQueryVo;
import com.gloryroad.demo.entity.system.SystemGroup;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class SystemGroupDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询分组通过id
    public PageModel<SystemGroup> getSystemGroupById(PageModel<SystemGroup> page, Integer id){
        String sql = String.format("SELECT * FROM system_group where id = %s and status=0;", id);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<SystemGroup> systemGroupList = mappingObject(list);
        page.setResult(systemGroupList);
        return page;
    }

    public List<SystemGroup> getSystemGroupById(Integer id){
        String sql = String.format("SELECT * FROM system_group where id = %s and status=0;", id);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<SystemGroup> systemGroupList = mappingObject(list);
        return systemGroupList;
    }

    /** 通过父id 查找其下的分组集合*/
    public List<SystemGroup> getSystemGroupByParentId(Integer parentId){
        String sql = String.format("SELECT * FROM system_group where parent_id = %s and status=0;", parentId);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<SystemGroup> systemGroupList = mappingObject(list);
        return systemGroupList;
    }

    // 查询分组通过条件
    public PageModel<SystemGroup> getSystemGroups(PageModel<SystemGroup> page, SystemGroupQueryVo systemGroupQueryVo){
        int pageNo = systemGroupQueryVo.getPageNo();
        int pageSize = systemGroupQueryVo.getPageSize();

        String sql = "SELECT * FROM system_group where status=0";
        if (systemGroupQueryVo.getGroupSign() != null){
            sql += " and group_sign like '"+ systemGroupQueryVo.getGroupSign() +"%'";
        }
        if (systemGroupQueryVo.getGroupName() != null){
            sql += " and group_name like '"+ systemGroupQueryVo.getGroupName() +"%'";
        }

        sql += String.format(" order by %s limit %s,%s;", systemGroupQueryVo.getSort(),  (pageNo-1) * pageNo, pageSize);
        System.out.println("sql = " + sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<SystemGroup> systemGroupList = mappingObject(list);
        page.setResult(systemGroupList);
        return page;
    }

    // 插入分组
    public int insertSystemGroups(SystemGroup systemGroup){

        String sql = "insert into system_Group(group_name, group_sign, description, createTime) " +
                "values('%s','%s','%s',%s)";
        sql = String.format(sql, systemGroup.getGroupName(), systemGroup.getGroupSign(), systemGroup.getDescription(),
                            systemGroup.getCreateTime());
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 更改分组
    public int updateSystemGroups(SystemGroup systemGroup){

        String sql = "update system_group set createtime=" + systemGroup.getCreateTime();
        if (systemGroup.getGroupName() != null){
            sql += ",group_name=" + systemGroup.getGroupName();
        }
        if (systemGroup.getGroupSign() != null){
            sql += ",group_sign='" + systemGroup.getGroupSign() + "'";
        }
        if(systemGroup.getDescription() != null) {
            sql += ",description='" + systemGroup.getDescription() + "'";
        }
        sql += " where id = " + systemGroup.getId() + ";";
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 删除分组
    public int deleteSystemGroups(String[] ids){

        String sql = "update system_group set status=1 where id in (?);";
        String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<SystemGroup> mappingObject(List<Map<String,Object>> list){
        List<SystemGroup> systemGroupList = new LinkedList<>();
        for(Map<String,Object> map: list){
            SystemGroup systemGroup = new SystemGroup();
            systemGroup.setId((Integer) map.get("id"));
            systemGroup.setParentId((Integer) map.get("parent_id"));
            systemGroup.setGroupSign((String) map.get("group_sign"));
            systemGroup.setGroupName((String) map.get("group_name"));
            systemGroup.setDescription((String) map.get("description"));
            systemGroup.setStatus((Integer) map.get("status"));
            systemGroup.setCreateTime((Integer) map.get("createTime"));
            systemGroupList.add(systemGroup);
            System.out.println("systemGroup = " + systemGroup);
        }
        return systemGroupList;
    }
}
