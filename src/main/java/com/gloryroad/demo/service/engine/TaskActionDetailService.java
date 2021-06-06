package com.gloryroad.demo.service.engine;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.constant.EngineConstant;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.engine.EngineActionBasicDao;
import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.entity.engine.EngineActionBasic;
import com.gloryroad.demo.entity.session.IUser;
import com.gloryroad.demo.service.cases.CasesBasicService;
import com.gloryroad.demo.utils.IpUtil;
import com.gloryroad.demo.utils.TimesUtil;
import com.gloryroad.demo.utils.session.UserUtil;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class TaskActionDetailService {

    @Autowired
    private EngineActionBasicDao engineActionBasicDao;

    @Autowired
    private CasesBasicService casesBasicService;

    @Autowired
    private UserUtil userUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskActionDetailService.class);

    /** 执行任务内容获取 */
    public List<CasesBasicDto> getTaskAction(HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("getTaskAction ip {}", ip);

        List<CasesBasicDto> casesBasicDtos = Lists.newArrayList();
        EngineActionBasic engineActionBasic = engineActionBasicDao.getEngineActionBasic();
        if(engineActionBasic == null){
            return casesBasicDtos;
        }

        /* 判断是否有需要执行的任务 */
        if(engineActionBasic.getStartTime() > System.currentTimeMillis()){
            return casesBasicDtos;
        }

        /* 把需要执行的任务从队列里面删除 */
        if(engineActionBasicDao.deleteEngineActionBasic(engineActionBasic) <= 0){
            LOGGER.error("删除旧任务失败 {}", JSONObject.toJSONString(engineActionBasic));
        }
        engineActionBasicDao.deleteTaskHash(engineActionBasic.getId());

        /* 任务内容详情获取 */
        List<String> casesIds = engineActionBasic.getCasesIds();
        casesBasicDtos = casesBasicService.findCasesBasics(casesIds, request);

        /* 判断是否要插入新任务执行 */
        long newActionTime = obtainTaskNewActionTime(engineActionBasic);
        if (newActionTime > 0){
            engineActionBasic.setStartTime(newActionTime);
            engineActionBasicDao.insertEngineActionBasic(engineActionBasic);
            engineActionBasicDao.insertTaskHash(engineActionBasic.getId(), engineActionBasic);
        }
        return casesBasicDtos;
    }

    /** 发起新的执行任务 */
    public int insertTaskAction(HttpServletRequest request, EngineActionBasic engineActionBasic, Map<String, String> messageMap){
        IUser user = userUtil.getUserSession(request);
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("getTaskAction ip {} user {}", ip, user.getName());

        if(engineActionBasicDao.getEngineActionBasicNum() > EngineConstant.TASK_RUN_COUNT_BORDER){
            messageMap.put("errmsg", "任务已经在队列中");
            return ResCode.C1001;
        }
        engineActionBasic.setCreateAccount(user.getAccount());
        engineActionBasic.setMail(user.getMail());
        if(!engineActionBasicDao.insertEngineActionBasic(engineActionBasic)){
            messageMap.put("errmsg", "任务开始失败");
            return ResCode.C1008;
        }
        return ResCode.C0;
    }

    /** 执行任务删除 */
    public int deleteTaskAction(Integer taskId, HttpServletRequest request, Map<String, String> messageMap){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("getTaskAction ip {}", ip);
        String engineActionBasicString = engineActionBasicDao.getTaskHash(taskId);

        if(engineActionBasicString == null){
            messageMap.put("errmsg", "任务执行队列中不存在此任务");
            return ResCode.C1008;
        }

        if(engineActionBasicDao.deleteEngineActionBasic(engineActionBasicString) == 0){
            messageMap.put("errmsg", "删除任务执行失败");
            return ResCode.C1008;
        }

        return ResCode.C0;
    }

    /** 返回新任务时间 */
    public long obtainTaskNewActionTime(EngineActionBasic engineActionBasic){
        LOGGER.info("obtainTaskActionTime start");
        long newActionTime = 0;
        if(engineActionBasic.getRunMode().equals(GloryRoadEnum.RunMode.PERIOD_ONCE)){
            return newActionTime;
        }

        if(engineActionBasic.getRunMode().equals(GloryRoadEnum.RunMode.PERIOD_SECOND)){
            newActionTime = engineActionBasic.getStartTime() + TimesUtil.secondToMillisecond(engineActionBasic.getRunModeContent());
        }

        if(engineActionBasic.getRunMode().equals(GloryRoadEnum.RunMode.PERIOD_MINUTE)){
            newActionTime = engineActionBasic.getStartTime() + TimesUtil.minuteToMillisecond(engineActionBasic.getRunModeContent());
        }

        if(engineActionBasic.getRunMode().equals(GloryRoadEnum.RunMode.PERIOD_HOUR)){
            newActionTime = engineActionBasic.getStartTime() + TimesUtil.hourToMillisecond(engineActionBasic.getRunModeContent());
        }

        if(engineActionBasic.getRunMode().equals(GloryRoadEnum.RunMode.PERIOD_DAY)){
            newActionTime = engineActionBasic.getStartTime() + TimesUtil.dayToMillisecond(engineActionBasic.getRunModeContent());
        }

        if(engineActionBasic.getRunMode().equals(GloryRoadEnum.RunMode.PERIOD_WEEK)){
            newActionTime = engineActionBasic.getStartTime() + TimesUtil.weekToMillisecond(engineActionBasic.getRunModeContent());
        }

        if(engineActionBasic.getRunMode().equals(GloryRoadEnum.RunMode.PERIOD_MONTH)){
            newActionTime = engineActionBasic.getStartTime() + TimesUtil.monthToMillisecond(engineActionBasic.getRunModeContent(), engineActionBasic.getStartTime());
        }

        return newActionTime;
    }
}
