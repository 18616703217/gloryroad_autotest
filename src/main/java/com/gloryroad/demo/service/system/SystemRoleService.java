package com.gloryroad.demo.service.system;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.system.SystemUserQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.system.SystemUserDao;
import com.gloryroad.demo.entity.system.SystemGroup;
import com.gloryroad.demo.entity.system.SystemUser;
import com.gloryroad.demo.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class SystemRoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemRoleService.class);

    /** 角色信息查找 */
    public List<String> findSystemRoles(HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {}", ip);

        return GloryRoadEnum.Role.getNames();
    }
}
