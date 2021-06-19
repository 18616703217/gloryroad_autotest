package com.gloryroad.demo.dao.session;

import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.constant.RedisKey;
import com.gloryroad.demo.entity.session.IUser;
import com.gloryroad.demo.entity.system.SystemUser;
import com.gloryroad.demo.utils.*;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Repository
public class IUserDao {

    @Autowired
    private GeneralRedis generalRedis;

    /**
     * 保存用户登录信息
     * @param user
     */
    public String saveUserSession(SystemUser user){
        String token = Md5Util.getMd5(user.getAccount());
        String key = String.format("%s%s", RedisKey.SESSION_TOKEN,token);
        String userKey=String.format("%s%s", RedisKey.SESSION_USER,token);
        String exireToken=(String) generalRedis.get(key);
        String userValue=(String) generalRedis.get(userKey);
        if(StringUtil.isNotBlank(exireToken) && StringUtil.isNotBlank(userValue)){
            return token;
        }
        /** token相关信息*/
        generalRedis.set(key, token);
        generalRedis.expire(key, RedisKey.KEY_SESSION_EXPIRE_SECOND);

        //用户相关信息
        generalRedis.set(userKey, JsonUtil.beanToJson(user));
        generalRedis.expire(userKey, RedisKey.KEY_SESSION_EXPIRE_SECOND);
        return token;
    }

    public SystemUser getUserByToken(HttpServletRequest request){
        String token = HttpUtil.getToken(request);
        String key = String.format("%s%s", RedisKey.SESSION_TOKEN,token);
        String userKey=String.format("%s%s", RedisKey.SESSION_USER,token);
        String keyValue=(String) generalRedis.get(key);
        String user = (String) generalRedis.get(userKey);
        SystemUser systemUser = null;
        if(StringUtil.isNotBlank(user) && StringUtil.isNotBlank(keyValue)){
            JSONObject obj = JSONObject.parseObject(user);
            systemUser = JSONObject.toJavaObject(obj,SystemUser.class);
        }
        return systemUser;

    }

    /**
     * 删除redis用户信息
     * @param request
     */
    public void deleteRedis(HttpServletRequest request){
        String token = HttpUtil.getToken(request);
        System.out.println("deleteRedis token = " + token);
        if(StringUtil.isNotBlank(token)){
            String key = String.format("%s%s", RedisKey.SESSION_TOKEN,token);
            String userKey = String.format("%s%s", RedisKey.SESSION_USER,token);
            System.out.println("deleteRedis key = " + key);
            if(StringUtil.isNotBlank(key)){
                generalRedis.del(key);
            }
            if(StringUtil.isNotBlank(userKey)){
                generalRedis.del(userKey);
            }

        }
    }

    public void saveSessionId(String mail,String sessionId){
        if(StringUtil.isNotBlank(sessionId) && StringUtil.isNotBlank(mail)){
            String key = String.format("%s%s", RedisKey.SESSION_ID_PREFIX, Md5Util.getMd5(mail));
            generalRedis.set(key, sessionId);
            generalRedis.expire(key, RedisKey.KEY_SESSION_EXPIRE_SECOND);
        }
    }

    public String getSessionId(String mail){
        String sessionId = null;
        if(StringUtil.isNotBlank(mail)){
            String key = String.format("%s%s", RedisKey.SESSION_ID_PREFIX, Md5Util.getMd5(mail));
            sessionId = (String) generalRedis.get(key);
        }
        return sessionId;
    }
}
