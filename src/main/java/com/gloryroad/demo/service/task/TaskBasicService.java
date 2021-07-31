package com.gloryroad.demo.service.task;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.Vo.task.TaskBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.interfac.InterfacBasicDao;
import com.gloryroad.demo.dao.task.TaskBasicDao;
import com.gloryroad.demo.dto.TaskBasicDto;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.gloryroad.demo.entity.session.IUser;
import com.gloryroad.demo.entity.task.TaskBasic;
import com.gloryroad.demo.service.cases.CasesBasicService;
import com.gloryroad.demo.service.interfac.InterfacAssertService;
import com.gloryroad.demo.service.system.SystemGroupService;
import com.gloryroad.demo.utils.IpUtil;
import com.gloryroad.demo.utils.TimesUtil;
import com.gloryroad.demo.utils.session.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class TaskBasicService {

    @Autowired
    private TaskBasicDao taskBasicDao;

    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private CasesBasicService casesBasicService;

    @Autowired
    private UserUtil userUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskBasicService.class);

    /** 任务信息查找 */
    public PageModel<TaskBasicDto> finds(TaskBasicQueryVo taskBasicQueryVo, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} TaskBasicQueryVo {}", ip, JSON.toJSONString(taskBasicQueryVo));
        List<TaskBasicDto> taskBasicDtos;
        PageModel<TaskBasicDto> page = new PageModel();
        if(taskBasicQueryVo.getId() != null){
            taskBasicDtos = taskBasicDao.getTaskBasicById(taskBasicQueryVo.getId());
            page.setResult(taskBasicDtos);
            return page;
        }else {
             taskBasicDtos = taskBasicDao.getTaskBasics(taskBasicQueryVo);
        }

        for(TaskBasicDto taskBasicDto: taskBasicDtos){
            taskBasicDto.setGroupName(systemGroupService.findSystemGroupById(taskBasicDto.getGroupId()).getGroupName());
            taskBasicDto.setCasesBasicList(casesBasicService.findCasesBasics(taskBasicDto.getCasesIds()));
        }
        page.setResult(taskBasicDtos);
        return page;
    }

    /** 任务信息插入 */
    public int insertTaskBasics(TaskBasic taskBasic, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        IUser user = userUtil.getUserSession(request);
        LOGGER.info("insert ip {} taskBasic {}", ip, JSON.toJSONString(taskBasic));

        if(taskBasic == null
                || taskBasic.getTaskName() == null
                || taskBasic.getGroupId() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        taskBasic.setCreateAccount(user.getAccount());
        taskBasic.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));
        if(taskBasicDao.insertTaskBasic(taskBasic) == 1){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "插入任务信息失败");
        return ResCode.C1008;
    }

    /** 任务信息更改 */
    public int updateInterfacBasic(TaskBasic taskBasic, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} taskBasic {}", ip, JSON.toJSONString(taskBasic));

        if(taskBasic == null || taskBasic.getId() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        taskBasic.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));
        if(taskBasicDao.updateTaskBasic(taskBasic) == 1){
            return ResCode.C0;
        }
        messageMap.put("errmsg", "更改任务信息失败");
        return ResCode.C1008;
    }

    /** 任务信息删除 */
    public int deleteTaskBasics(Integer[] ids, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("delete ip {} ids {}", ip, JSON.toJSONString(ids));

        if(ids==null || ids.length==0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        if(taskBasicDao.deleteTaskBasics(ids) > 0){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "删除任务信息失败");
        return ResCode.C1008;
    }
}
