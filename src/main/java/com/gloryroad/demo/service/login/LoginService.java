package com.gloryroad.demo.service.login;

import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.session.IUserDao;
import com.gloryroad.demo.dao.system.SystemUserDao;
import com.gloryroad.demo.entity.system.SystemUser;
import com.gloryroad.demo.service.system.SystemGroupService;
import com.gloryroad.demo.service.system.SystemUserService;
import com.gloryroad.demo.utils.IpUtil;
import com.gloryroad.demo.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private SystemUserDao systemUserDao;

    @Autowired
    private IUserDao iUserDao;

    /** 登录 */
    public int checkSystemUser(String account, String passwd, Map<String, String> messageMap,
                               HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("login ip {}", ip);

        if(StringUtil.isBlank(account) || StringUtil.isBlank(passwd)){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        List<SystemUser> systemUsers = systemUserDao.checkSystemUser(account, passwd);
        if(systemUsers.size() == 0){
            messageMap.put("errmsg", "校验失败");
            return ResCode.C1008;
        }

        iUserDao.saveUserSession(systemUsers.get(0));
        return ResCode.C0;
    }

    /** 退出登录 */
    public int logout(HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("login ip {}", ip);
        iUserDao.deleteRedis(request);
        return ResCode.C0;
    }
}
