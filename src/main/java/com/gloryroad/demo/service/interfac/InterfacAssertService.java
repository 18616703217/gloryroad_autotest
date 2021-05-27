package com.gloryroad.demo.service.interfac;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.interfac.InterfacAssertDao;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.interfac.InterfacAssert;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.gloryroad.demo.utils.IpUtil;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class InterfacAssertService {
    
    @Autowired
    private InterfacAssertDao interfacAssertDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterfacAssertService.class);

    /** 接口断言信息查找 */
    public List<InterfacAssert> findInterfacAsserts(Integer interfacId, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} interfacId {}", ip, interfacId);
        List<InterfacAssert> interfacAsserts = Lists.newArrayList();
        if(interfacId == null){
            return interfacAsserts;
        }
        interfacAsserts = interfacAssertDao.getInterfacAssertsByInterfacId(interfacId);
        return interfacAsserts;
    }

    /** 接口断言信息插入 */
    public int insertInterfacAssert(InterfacAssert interfacAssert, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} interfacAssert {}", ip, JSON.toJSONString(interfacAssert));

        if(interfacAssert == null
                || interfacAssert.getInterfacId() == null
                || interfacAssert.getAssertPosition() == null
                || interfacAssert.getAssertContent() == null
                || interfacAssert.getCreateAccount() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        interfacAssert.setCreateTime(System.currentTimeMillis());
        if(interfacAssertDao.insertInterfacAssert(interfacAssert) == 1){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "插入接口断言信息失败");
        return ResCode.C1008;
    }

    /** 接口断言信息更改 */
    public int updateInterfacAssert(InterfacAssert interfacAssert, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} interfacAssert {}", ip, JSON.toJSONString(interfacAssert));

        if(interfacAssert == null || interfacAssert.getId() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        if(interfacAssertDao.updateInterfacAssert(interfacAssert) == 1){
            return ResCode.C0;
        }
        messageMap.put("errmsg", "更改接口断言信息失败");
        return ResCode.C1008;
    }

    /** 接口断言信息删除 */
    public int deleteInterfacAsserts(Integer[] ids, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("delete ip {} ids {}", ip, JSON.toJSONString(ids));

        if(ids==null || ids.length==0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        if(interfacAssertDao.deleteInterfacAsserts(ids) > 0){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "删除接口断言信息失败");
        return ResCode.C1008;
    }
}
