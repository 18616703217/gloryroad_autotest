package com.gloryroad.demo.service.system;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.ResponseModel;
import com.gloryroad.demo.Vo.system.SystemUserQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.session.IUserDao;
import com.gloryroad.demo.dao.system.SystemUserDao;
import com.gloryroad.demo.dto.system.SystemUserDto;
import com.gloryroad.demo.entity.session.IUser;
import com.gloryroad.demo.entity.system.SystemGroup;
import com.gloryroad.demo.entity.system.SystemUser;
import com.gloryroad.demo.utils.GeneralRedis;
import com.gloryroad.demo.utils.IpUtil;
import com.gloryroad.demo.utils.StringUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

@Service
public class SystemUserService {

    @Autowired
    private SystemUserDao systemUserDao;

    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private IUserDao iUserDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUserService.class);

    /** 用户信息查找 */
    public PageModel<SystemUserDto> findSystemUsers(SystemUserQueryVo systemUserQueryVo, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} systemUserQueryVo {}", ip, JSON.toJSONString(systemUserQueryVo));

        PageModel<SystemUserDto> page = new PageModel();
        List<SystemUserDto> systemUserList;
        if(systemUserQueryVo.getId() != null){

            systemUserList = systemUserDao.getSystemUserById(systemUserQueryVo.getId());
        }
        else {
            systemUserList = systemUserDao.getSystemUsers(systemUserQueryVo);
        }

        for(SystemUserDto systemUser: systemUserList){
            systemUser.setGroupName(systemGroupService.findSystemGroupById(systemUser.getGroupId()).getGroupName());
        }
        page.setResult(systemUserList);
        return page;
    }

    /** 用户信息插入 */
    public int insertSystemUsers(SystemUser systemUser, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} systemUser {}", ip, JSON.toJSONString(systemUser));

        if(systemUser == null
                || systemUser.getGroupId() == null
                || systemUser.getAccount() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        SystemGroup systemGroup = systemGroupService.findSystemGroupById(systemUser.getGroupId());
        if(systemGroup == null){
            messageMap.put("errmsg", "业务分组不存在");
            return ResCode.C1006;
        }

        systemUser.setCreateTime(System.currentTimeMillis());
        if(systemUserDao.insertSystemUsers(systemUser) == 1){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "插入用户信息失败");
        return ResCode.C1008;
    }

    /** 用户信息更改 */
    public int updateSystemUser(SystemUser systemUser, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} systemUser {}", ip, JSON.toJSONString(systemUser));

        if(systemUser == null || systemUser.getId() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        systemUser.setCreateTime(System.currentTimeMillis());
        if(systemUserDao.updateSystemUsers(systemUser) == 1){
            return ResCode.C0;
        }
        messageMap.put("errmsg", "更改用户信息失败");
        return ResCode.C1008;
    }

    /** 用户信息删除 */
    public int deleteSystemUsers(String[] ids, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("delete ip {} ids {}", ip, JSON.toJSONString(ids));

        if(ids==null || ids.length==0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        if(systemUserDao.deleteSystemUsers(ids) > 0){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "删除用户信息失败");
        return ResCode.C1008;
    }
}
