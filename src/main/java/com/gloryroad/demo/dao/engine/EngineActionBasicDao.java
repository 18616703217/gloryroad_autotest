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

    // 通过json获取执行实体
    public Boolean insertEngineActionBasic(EngineActionBasic engineActionBasic, long actionTime){
        String engineActionBasicString = JSONObject.toJSONString(engineActionBasic)
        return generalRedis.zadd(EngineConstant.TASK_RUN_QUEUE_ZSET, actionTime, engineActionBasicString);
    }

    // 通过json获取执行实体
    public Boolean insertEngineActionBasic(EngineActionBasic engineActionBasic, long actionTime){
        String engineActionBasicString = JSONObject.toJSONString(engineActionBasic)
        return generalRedis.zadd(EngineConstant.TASK_RUN_QUEUE_ZSET, actionTime, engineActionBasicString);
    }

}
