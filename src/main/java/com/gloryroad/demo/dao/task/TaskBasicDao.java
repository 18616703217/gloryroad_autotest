package com.gloryroad.demo.dao.task;

import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.Vo.task.TaskBasicQueryVo;
import com.gloryroad.demo.dto.TaskBasicDto;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.task.TaskBasic;
import com.gloryroad.demo.utils.TimesUtil;
import com.google.common.base.Joiner;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class TaskBasicDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询任务信息通过id
    public List<TaskBasicDto> getTaskBasicById(Integer id){
        String sql = String.format("SELECT * FROM task_basic where id = %s and status = 0;", id);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        System.out.println(list);
        List<TaskBasicDto> interfacBasicList = mappingObject(list);
        return interfacBasicList;
    }

    // 查询任务信息通过条件
    public List<TaskBasicDto> getTaskBasics(TaskBasicQueryVo taskBasicQueryVo){
        int pageNo = taskBasicQueryVo.getPageNo();
        int pageSize = taskBasicQueryVo.getPageSize();

        String sql = "SELECT * FROM task_basic where status = 0";
        if (taskBasicQueryVo.getTaskName() != null){
            sql += " and task_name like '"+ taskBasicQueryVo.getTaskName() +"%'";
        }
        if (taskBasicQueryVo.getCreateAccount() != null){
            sql += " and create_account like '"+ taskBasicQueryVo.getCreateAccount() +"%'";
        }
        if (taskBasicQueryVo.getGroupId() != null){
            sql += " and group_id = "+ taskBasicQueryVo.getGroupId();
        }

        sql += String.format(" order by %s limit %s,%s;", taskBasicQueryVo.getSort(),  (pageNo-1) * pageNo, pageSize);
        System.out.println("sql = " + sql);
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        List<TaskBasicDto> taskBasicDtos = mappingObject(list);
        return taskBasicDtos;
    }

    // 插入任务信息
    public int insertTaskBasic(TaskBasic taskBasic){

        String sql = "insert into task_basic(cases_ids, task_name, remark, group_id, create_account, createTime) " +
                "values('%s','%s','%s',%s,'%s',%s)";

        sql = String.format(sql, Joiner.on(',').join(taskBasic.getCasesIds()), taskBasic.getTaskName(),
                taskBasic.getRemark(), taskBasic.getGroupId(), taskBasic.getCreateAccount(), taskBasic.getCreateTime());

        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);
        return actionNum;
    }

    // 更改任务信息
    public int updateTaskBasic(TaskBasic taskBasic){

        String sql = "update task_basic set createtime=" + taskBasic.getCreateTime();
        if (taskBasic.getCasesIds() != null){
            sql += ",cases_ids='" + Joiner.on(',').join(taskBasic.getCasesIds()) + "'";
        }
        if (taskBasic.getTaskName() != null){
            sql += ",task_name='" + taskBasic.getTaskName() + "'";
        }

        if (taskBasic.getRemark() != null){
            sql += ",remark='" + taskBasic.getRemark() + "'";
        }

        sql += " where id = " + taskBasic.getId() + ";";
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);
        return actionNum;
    }

    // 删除任务信息
    public int deleteTaskBasics(Integer[] ids){

        String sql = "update task_basic set status=1 where id in (%s);";
        sql = String.format(sql, Joiner.on(',').join(ids));
        System.out.println(sql);
        int actionNum = jdbcTemplate.update(sql);

        return actionNum;
    }

    // 数据库对象和数据库查询结果映射
    private static List<TaskBasicDto> mappingObject(List<Map<String,Object>> list){
        List<TaskBasicDto> taskBasics = Lists.newArrayList();
        for(Map<String,Object> map: list){
            TaskBasicDto taskBasic = new TaskBasicDto();
            taskBasic.setId((Integer) map.get("id"));
            String casesIds = (String) map.get("cases_ids");
            taskBasic.setCasesIds(Arrays.asList(casesIds.split(",")));
            taskBasic.setCreateAccount((String) map.get("create_account"));
            taskBasic.setTaskName((String) map.get("task_name"));
            taskBasic.setStatus((Integer) map.get("status"));
            taskBasic.setCreateTime((Integer) map.get("createTime"));
            taskBasic.setCreateDate(TimesUtil.timeStamp2Date((Integer) map.get("createTime"), null));
            taskBasic.setRemark((String) map.get("remark"));
            taskBasic.setGroupId((Integer) map.get("group_id"));
            taskBasics.add(taskBasic);
            System.out.println("taskBasic = " + taskBasic);
        }
        return taskBasics;
    }
}
