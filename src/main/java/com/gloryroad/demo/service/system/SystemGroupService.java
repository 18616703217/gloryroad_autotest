package com.gloryroad.demo.service.system;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.system.SystemGroupQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.system.SystemGroupDao;
import com.gloryroad.demo.dao.system.SystemGroupDao;
import com.gloryroad.demo.entity.system.SystemGroup;
import com.gloryroad.demo.entity.system.SystemGroup;
import com.gloryroad.demo.utils.IpUtil;
import com.gloryroad.demo.utils.TimesUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class SystemGroupService {
    @Autowired
    private SystemGroupDao systemGroupDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemGroupService.class);

    /** 分组信息查找 */
    public PageModel<SystemGroup> findSystemGroups(SystemGroupQueryVo systemGroupQueryVo, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} systemGroupQueryVo {}", ip, JSON.toJSONString(systemGroupQueryVo));

        PageModel<SystemGroup> page = new PageModel();
        if(systemGroupQueryVo.getId() != null){
            return systemGroupDao.getSystemGroupById(page, systemGroupQueryVo.getId());
        }
        return systemGroupDao.getSystemGroups(page, systemGroupQueryVo);
    }

    /** 分组信息插入 */
    public int insertSystemGroups(SystemGroup systemGroup, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} systemGroup {}", ip, JSON.toJSONString(systemGroup));

        if(systemGroup == null || systemGroup.getId() != null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        systemGroup.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));
        if(systemGroupDao.insertSystemGroups(systemGroup) == 1){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "插入分组信息失败");
        return ResCode.C1008;
    }

    /** 分组信息更改 */
    public int updateSystemGroup(SystemGroup systemGroup, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} systemGroup {}", ip, JSON.toJSONString(systemGroup));

        if(systemGroup == null || systemGroup.getId() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        systemGroup.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));
        if(systemGroupDao.updateSystemGroups(systemGroup) == 1){
            return ResCode.C0;
        }
        messageMap.put("errmsg", "更改分组信息失败");
        return ResCode.C1008;
    }

    /** 分组信息删除 */
    public int deleteSystemGroups(String[] ids, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("delete ip {} ids {}", ip, JSON.toJSONString(ids));

        if(ids==null || ids.length==0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        if(systemGroupDao.deleteSystemGroups(ids) > 0){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "删除分组信息失败");
        return ResCode.C1008;
    }

    /** 分组信息查找 */
    public SystemGroup findSystemGroupById(Integer groupId){
        LOGGER.info("groupId {}", groupId);
        SystemGroup systemGroup = null;

        if(groupId == null){
            return systemGroup;
        }
        try{
            systemGroup = systemGroupDao.getSystemGroupById(groupId).get(0);
        }catch (Exception e){
            LOGGER.error("findSystemGroupById 分组信息查找失败");
        }
        return systemGroup;
    }
}
