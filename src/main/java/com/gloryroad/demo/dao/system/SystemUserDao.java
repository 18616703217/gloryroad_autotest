package com.gloryroad.demo.dao.system;

import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.system.SystemUserQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.entity.system.SystemUser;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class SystemUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询用户通过id
    public PageModel<SystemUser> getSystemUserById(PageModel<SystemUser> page, Integer id){
        String sql = String.format("SELECT * FROM system_user where id = %s and status = 0;", id);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<SystemUser> systemUserList = mappingObject(list);
        page.setResult(systemUserList);
        return page;
    }

    // 查询用户通过条件
    public List<SystemUser> checkSystemUser(String account, String passwd){

        String sql = "SELECT * FROM system_user where status = 0"
                + " and account = '"+ account +"'"
                + " and passwd = '" + passwd +"';";


        System.out.println("checkSystemUser sql = " + sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<SystemUser> systemUserList = mappingObject(list);
        return systemUserList;
    }

    // 查询用户通过条件
    public PageModel<SystemUser> getSystemUsers(PageModel<SystemUser> page, SystemUserQueryVo systemUserQueryVo){
        int pageNo = systemUserQueryVo.getPageNo();
        int pageSize = systemUserQueryVo.getPageSize();

        String sql = "SELECT * FROM system_user where status = 0";
        if (systemUserQueryVo.getAccount() != null){
            sql += " and account like '"+ systemUserQueryVo.getAccount() +"%'";
        }
        if (systemUserQueryVo.getName() != null){
            sql += " and name like '"+ systemUserQueryVo.getName() +"%'";
        }

        sql += String.format(" order by %s limit %s,%s;", systemUserQueryVo.getSort(),  (pageNo-1) * pageNo, pageSize);
        System.out.println("sql = " + sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<SystemUser> systemUserList = mappingObject(list);
        page.setResult(systemUserList);
        return page;
    }

    // 插入用户
    public int insertSystemUsers(SystemUser systemUser){

        String sql = "insert into system_user(account, name, role, groupSign, groupId, mail, createTime) " +
                "values('%s','%s','%s','%s',%s,'%s',%s)";
        sql = String.format(sql, systemUser.getAccount(), systemUser.getName(), systemUser.getRole().getRoleName(),
                systemUser.getGroupSign(), systemUser.getGroupId(), systemUser.getMail(), systemUser.getCreateTime());
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 更改用户
    public int updateSystemUsers(SystemUser systemUser){

        String sql = "update system_user set createtime=" + systemUser.getCreateTime();
        if (systemUser.getMail() != null){
            sql += ",mail='" + systemUser.getMail() + "'";
        }
        if (systemUser.getGroupSign() != null){
            sql += ",group_sign='" + systemUser.getGroupSign() + "'";
            sql += ",group_id=" + systemUser.getId();
        }
        if(systemUser.getRole() != null){
            sql += ",role='" + systemUser.getRole().getRoleName() + "'";
        }
        if(systemUser.getName() != null){
            sql += ",name='" + systemUser.getName() + "'";
        }
        sql += " where id = " + systemUser.getId() + ";";
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 删除用户
    public int deleteSystemUsers(String[] ids){

        String sql = "update system_user set status=1 where id in (%s);";
        String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<SystemUser> mappingObject(List<Map<String,Object>> list){
        List<SystemUser> systemUserList = new LinkedList<>();
        for(Map<String,Object> map: list){
            SystemUser systemUser = new SystemUser();
            systemUser.setId((Integer) map.get("id"));
            systemUser.setAccount((String) map.get("account"));
            systemUser.setName((String) map.get("name"));
            systemUser.setRole(GloryRoadEnum.Role.getRole((String) map.get("role")));
            systemUser.setGroupSign((String) map.get("group_sign"));
            systemUser.setGroupId((Integer) map.get("group_id"));
            systemUser.setMail((String) map.get("mail"));
            systemUser.setStatus((Integer) map.get("status"));
            systemUser.setCreateTime((Integer) map.get("createTime"));
            systemUserList.add(systemUser);
            System.out.println("systemUser = " + systemUser);
        }
        return systemUserList;
    }
}
