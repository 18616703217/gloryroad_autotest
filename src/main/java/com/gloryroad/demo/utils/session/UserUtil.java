package com.gloryroad.demo.utils.session;

import com.gloryroad.demo.dao.session.IUserDao;
import com.gloryroad.demo.dao.system.SystemUserDao;
import com.gloryroad.demo.entity.session.BuiltInUser;
import com.gloryroad.demo.entity.session.IUser;
import com.gloryroad.demo.entity.system.SystemUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Repository
public class UserUtil {
    private Logger LOGGER = LoggerFactory.getLogger(UserUtil.class);

    @Autowired
    private IUserDao iUserDao;

    /**
     * 获取session
     *
     * @param request
     * @return
     */
    public IUser getSession(final HttpServletRequest request) {
		SystemUser systemUser = iUserDao.getUserByToken(request);
        BuiltInUser builtInUser = null;
		if (systemUser != null){
            builtInUser.setName(systemUser.getName());
            builtInUser.setMail(systemUser.getMail());
            builtInUser.setRole(systemUser.getRole());
            builtInUser.setAccount(systemUser.getAccount());
        }
        return builtInUser;
    }

    /**
     * 拦截器调用的验证用户是否登陆的逻辑
     *
     * @param request
     */
    public IUser getUserSession(final HttpServletRequest request) {
        IUser user;
        /** 直接session取值，如果有则直接返回 */
        user = this.getSession(request);

        /** 上线删除 */
//		user = getUser();
        return user;
    }

    /**
     * 测试使用
     */
    public IUser getUser(){
        IUser user = new BuiltInUser();
        ((BuiltInUser) user).setAccount("wenqiz");
        ((BuiltInUser) user).setMail("wenqiz@tujia.com");
        ((BuiltInUser) user).setName("张闻棋");
        ((BuiltInUser) user).setRole("测试");
        return user;
    }
}
