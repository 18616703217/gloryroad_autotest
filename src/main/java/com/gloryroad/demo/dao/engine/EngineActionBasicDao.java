package com.gloryroad.demo.dao.engine;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.constant.EngineConstant;
import com.gloryroad.demo.entity.engine.EngineActionBasic;
import com.gloryroad.demo.entity.interfac.InterfacAssert;
import com.gloryroad.demo.utils.GeneralRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class EngineActionBasicDao {
    @Autowired
    private GeneralRedis generalRedis;

    // 通过json获取执行实体
    public EngineActionBasic getEngineActionBasic(){
        Set<String> engineActionBasicSet = generalRedis.zrevrange(EngineConstant.TASK_RUN_QUEUE_ZSET, 0, 0);
        String engineActionBasicString = engineActionBasicSet.iterator().next();
        JSONObject engineActionBasicJson = JSONObject.parseObject(engineActionBasicString);
        EngineActionBasic engineActionBasic = JSONObject.toJavaObject(engineActionBasicJson,EngineActionBasic.class);
        return engineActionBasic;
    }

    // 插入执行任务
    public Boolean insertEngineActionBasic(EngineActionBasic engineActionBasic){
        String engineActionBasicString = JSONObject.toJSONString(engineActionBasic);
        return generalRedis.zadd(EngineConstant.TASK_RUN_QUEUE_ZSET, engineActionBasic.getStartTime(), engineActionBasicString);
    }

    // 插入任务id与任务内容映射
    public Boolean insertTaskHash(Integer taskId, EngineActionBasic engineActionBasic){
        String engineActionBasicString = JSONObject.toJSONString(engineActionBasic);
        return generalRedis.hset(EngineConstant.TASK_RUN_HASH, taskId.toString(), engineActionBasicString);
    }

    // 插入任务id与任务内容映射
    public String getTaskHash(Integer taskId){
        return JSONObject.toJSONString(generalRedis.hget(EngineConstant.TASK_RUN_HASH, taskId.toString()));
    }

    // 删除任务id与任务内容映射
    public void deleteTaskHash(Integer taskId){
        generalRedis.hdel(EngineConstant.TASK_RUN_HASH, taskId.toString());
    }

    // 获取当前执行中的任务数
    public long getEngineActionBasicNum(){
        return generalRedis.zCard(EngineConstant.TASK_RUN_QUEUE_ZSET);
    }

    // 获取当前执行中的任务数
    public long deleteEngineActionBasic(EngineActionBasic engineActionBasic){
        String engineActionBasicString = JSONObject.toJSONString(engineActionBasic);
        return generalRedis.zrem(EngineConstant.TASK_RUN_QUEUE_ZSET, engineActionBasicString);
    }

    // 获取当前执行中的任务数
    public long deleteEngineActionBasic(String engineActionBasicString){
        return generalRedis.zrem(EngineConstant.TASK_RUN_QUEUE_ZSET, engineActionBasicString);
    }

    // 记录执行过程缓存
    public void setTaskRunCache(String hkey, String value){
        generalRedis.hset(EngineConstant.TASK_RUN_CACHE, hkey, value);
    }

    // 获取执行过程缓存
    public String getTaskRunCache(String hkey){
        return (String) generalRedis.hget(EngineConstant.TASK_RUN_CACHE, hkey);
    }

}
