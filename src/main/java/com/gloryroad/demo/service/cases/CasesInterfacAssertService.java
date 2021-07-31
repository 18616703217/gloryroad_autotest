package com.gloryroad.demo.service.cases;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.cases.CasesInterfacAssertDao;
import com.gloryroad.demo.dao.interfac.InterfacAssertDao;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;
import com.gloryroad.demo.entity.interfac.InterfacAssert;
import com.gloryroad.demo.entity.session.IUser;
import com.gloryroad.demo.utils.IpUtil;
import com.gloryroad.demo.utils.TimesUtil;
import com.gloryroad.demo.utils.session.UserUtil;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class CasesInterfacAssertService {
    
    @Autowired
    private CasesInterfacAssertDao casesInterfacAssertDao;

    @Autowired
    private UserUtil userUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(CasesInterfacAssertService.class);

    /** 接口断言信息查找 */
    public List<CasesInterfacAssert> findCasesInterfacAsserts(Integer casesInterfacId, HttpServletRequest request){
        String ip = "";
        if(request == null){
            ip = IpUtil.getIpAddr(request);
        }

        LOGGER.info("find ip {} casesInterfacId {}", ip, casesInterfacId);
        List<CasesInterfacAssert> casesInterfacAsserts = Lists.newArrayList();
        if(casesInterfacId == null){
            return casesInterfacAsserts;
        }
        casesInterfacAsserts = casesInterfacAssertDao.getCasesInterfacAssertsByCasesInterfacId(casesInterfacId);
        return casesInterfacAsserts;
    }

    /** 接口断言信息插入 */
    public int insertCasesInterfacAsserts(List<CasesInterfacAssert> casesInterfacAsserts, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        IUser user = userUtil.getUserSession(request);
        LOGGER.info("insert ip {} casesInterfacAsserts {}", ip, JSON.toJSONString(casesInterfacAsserts));

        if(casesInterfacAsserts == null
                || casesInterfacAsserts.size() == 0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        for(CasesInterfacAssert casesInterfacAssert: casesInterfacAsserts) {
            if(casesInterfacAssert.getInterfacId() == null){
                messageMap.put("errmsg", "参数有误，关联用例接口id为空");
                return ResCode.C1002;
            }
            casesInterfacAssert.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));
            casesInterfacAssert.setCreateAccount(user.getAccount());
        }

        try {
            if(casesInterfacAssertDao.insertCasesInterfacAsserts(casesInterfacAsserts) == casesInterfacAsserts.size()){
                return ResCode.C0;
            }
        }catch (SQLException e){
            LOGGER.error("insertCasesInterfacAsserts 失败 {}", e.toString());
        }
        messageMap.put("errmsg", "插入接口断言信息失败");
        return ResCode.C1008;
    }

    /** 接口断言信息更改 */
    public int updateCasesInterfacAsserts(List<CasesInterfacAssert> casesInterfacAsserts, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} casesInterfacAsserts {}", ip, JSON.toJSONString(casesInterfacAsserts));

        if(casesInterfacAsserts == null || casesInterfacAsserts.size() == 0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        for(CasesInterfacAssert casesInterfacAssert: casesInterfacAsserts) {
            if(casesInterfacAssert.getId() == null){
                messageMap.put("errmsg", "参数有误，主键为空");
                return ResCode.C1002;
            }
        }
        try {
            if(casesInterfacAssertDao.updateCasesInterfacAsserts(casesInterfacAsserts) == casesInterfacAsserts.size()){
                return ResCode.C0;
            }
        }catch (SQLException e){
            LOGGER.error("updateCasesInterfacAsserts 失败 {}", e.toString());
        }

        messageMap.put("errmsg", "更改接口断言信息失败");
        return ResCode.C1008;
    }

    /** 接口断言信息删除 */
    public int deleteCasesInterfacAsserts(Integer[] ids, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("delete ip {} ids {}", ip, JSON.toJSONString(ids));

        if(ids==null || ids.length==0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        if(casesInterfacAssertDao.deleteCasesInterfacAsserts(ids) > 0){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "删除接口断言信息失败");
        return ResCode.C1008;
    }

    /** 接口断言信息删除 */
    public int deleteByCasesInterfacId(Integer casesInterfacId, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("deleteCasesInterfacAssertsByCasesInterfacId ip {} casesInterfacId {}", ip, JSON.toJSONString(casesInterfacId));

        if(casesInterfacId==null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        if(casesInterfacAssertDao.getCasesInterfacAssertsByCasesInterfacId(casesInterfacId).size() == 0){
            return ResCode.C0;
        }

        if(casesInterfacAssertDao.deleteByCasesInterfacId(casesInterfacId) > 0){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "删除接口断言信息失败");
        return ResCode.C1008;
    }
}
