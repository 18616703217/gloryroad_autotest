package com.gloryroad.demo.service.interfac;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.interfac.InterfacBasicDao;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.gloryroad.demo.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class InterfacBasicService {

    @Autowired
    private InterfacBasicDao interfacBasicDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterfacBasicService.class);

    /** 接口信息查找 */
    public PageModel<InterfacBasicDto> findInterfacBasics(InterfacBasicQueryVo interfacBasicQueryVo, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} InterfacBasicQueryVo {}", ip, JSON.toJSONString(interfacBasicQueryVo));
        List<InterfacBasicDto> interfacBasicList;
        PageModel<InterfacBasicDto> page = new PageModel();
        if(interfacBasicQueryVo.getId() != null){
            interfacBasicList = interfacBasicDao.getInterfacBasicById(interfacBasicQueryVo.getId());
            page.setResult(interfacBasicList);
            return page;
        }else {
             interfacBasicList = interfacBasicDao.getInterfacBasics(interfacBasicQueryVo);
        }

        page.setResult(interfacBasicList);
        return page;
    }

    /** 接口信息插入 */
    public int insertInterfacBasics(InterfacBasic interfacBasic, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} InterfacBasic {}", ip, JSON.toJSONString(interfacBasic));

        if(interfacBasic == null
                || interfacBasic.getInterfacName() == null
                || interfacBasic.getGroupId() == null
                || interfacBasic.getBodyType() == null
                || interfacBasic.getCreateAccount() == null
                || interfacBasic.getMethodType() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        if(!checkRequestInformation(interfacBasic)){
            messageMap.put("errmsg", "请求信息不合法");
            return ResCode.C1002;
        }
        interfacBasic.setCreateTime(System.currentTimeMillis());
        if(interfacBasicDao.insertInterfacBasic(interfacBasic) == 1){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "插入接口信息失败");
        return ResCode.C1008;
    }

    /** 接口信息拷贝 */
    public int copytInterfacBasic(Integer id, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} InterfacId {}", ip, id);

        if(id == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        List<InterfacBasicDto> interfacBasicList = interfacBasicDao.getInterfacBasicById(id);
        if(interfacBasicList.size() == 0){
            messageMap.put("errmsg", id+"接口不存在");
            return ResCode.C1001;
        }

        InterfacBasic interfacBasic = interfacBasicList.get(0);
        interfacBasic.setCreateTime(System.currentTimeMillis());
        if(interfacBasicDao.insertInterfacBasic(interfacBasic) == 1){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "拷贝接口信息失败");
        return ResCode.C1008;
    }

    /** 接口信息更改 */
    public int updateInterfacBasic(InterfacBasic interfacBasic, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} InterfacBasic {}", ip, JSON.toJSONString(interfacBasic));

        if(interfacBasic == null || interfacBasic.getId() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        if(checkRequestInformation(interfacBasic)){
            messageMap.put("errmsg", "POST请求无请求主体");
            return ResCode.C1002;
        }
        interfacBasic.setCreateTime(System.currentTimeMillis());
        if(interfacBasicDao.updateInterfacBasic(interfacBasic) == 1){
            return ResCode.C0;
        }
        messageMap.put("errmsg", "更改接口信息失败");
        return ResCode.C1008;
    }

    /** 接口信息删除 */
    public int deleteInterfacBasics(Integer[] ids, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("delete ip {} ids {}", ip, JSON.toJSONString(ids));

        if(ids==null || ids.length==0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        if(interfacBasicDao.deleteInterfacBasics(ids) > 0){
            return ResCode.C0;
        }

        messageMap.put("errmsg", "删除接口信息失败");
        return ResCode.C1008;
    }

    /** 校验请求信息内容是否合法 */
    private static boolean checkRequestInformation(InterfacBasic interfacBasic){
        return !interfacBasic.getMethodType().equals(GloryRoadEnum.CaseSubMethod.POST)
                || interfacBasic.getBodyType() != null;
    }
}
