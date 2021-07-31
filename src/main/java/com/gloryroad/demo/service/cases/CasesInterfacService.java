package com.gloryroad.demo.service.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.cases.CasesInterfacAssertDao;
import com.gloryroad.demo.dao.cases.CasesInterfacDao;
import com.gloryroad.demo.dao.interfac.InterfacBasicDao;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.cases.CasesInterfac;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.gloryroad.demo.service.interfac.InterfacAssertService;
import com.gloryroad.demo.service.system.SystemGroupService;
import com.gloryroad.demo.utils.IpUtil;
import com.gloryroad.demo.utils.TimesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class CasesInterfacService {

    @Autowired
    private CasesInterfacDao casesInterfacDao;

    @Autowired
    private CasesInterfacAssertService casesInterfacAssertService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CasesInterfacService.class);

    /** 接口信息查找 */
    public CasesInterfacDto findById(Integer id, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} casesInterfacId {}", ip, id);
        CasesInterfacDto casesInterfacDto = new CasesInterfacDto();
        List<CasesInterfacDto> casesInterfacDtos = casesInterfacDao.getById(id);
        if(casesInterfacDtos.size() != 1){
            return casesInterfacDto;
        }
        casesInterfacDto = casesInterfacDtos.get(0);
        casesInterfacDto.setInterfacAsserts(casesInterfacAssertService.findCasesInterfacAsserts(casesInterfacDto.getId(), request));
        return casesInterfacDto;
    }

    /** 接口信息查找 */
    public List<CasesInterfacDto> findCasesInterfacsByCasesId(Integer casesBasicId){
        LOGGER.info("find casesBasicId {}", casesBasicId);
        List<CasesInterfacDto> casesInterfacDtos = casesInterfacDao.getCasesInterfacByCaeseId(casesBasicId);

        for(CasesInterfacDto casesInterfacDto: casesInterfacDtos){
            casesInterfacDto.setInterfacAsserts(casesInterfacAssertService.findCasesInterfacAsserts(casesInterfacDto.getId(), null));
        }
        return casesInterfacDtos;
    }

    /** 接口信息插入 */
    public int insertCasesInterfacs(List<CasesInterfacDto> casesInterfacDtos, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} casesInterfacs {}", ip, JSON.toJSONString(casesInterfacDtos));
        Integer newId;
        if(casesInterfacDtos == null
                || casesInterfacDtos.size() == 0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }

        for(CasesInterfacDto casesInterfacDto: casesInterfacDtos){
            if(!checkRequestInformation(casesInterfacDto)){
                messageMap.put("errmsg", "请求信息不合法");
                return ResCode.C1002;
            }
            casesInterfacDto.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));

        }
        try {
            for(CasesInterfacDto casesInterfacDto: casesInterfacDtos) {
                System.out.println("casesInterfacDto = " + JSONObject.toJSONString(casesInterfacDto));
                newId = casesInterfacDao.insertInterfacBasic(casesInterfacDto);

                if(newId == 0){
                    messageMap.put("errmsg", "插入接口信息失败");
                    return ResCode.C1008;
                }
                System.out.println("insert casesInterfacAsserts = " + casesInterfacDto.getInterfacAsserts());
                if(casesInterfacDto.getInterfacAsserts() == null
                        || casesInterfacDto.getInterfacAsserts().size() == 0){
                    continue;
                }

                for(CasesInterfacAssert casesInterfacAssert: casesInterfacDto.getInterfacAsserts()){
                    System.out.println("insert casesInterfacAssert = " + casesInterfacAssert);
                    casesInterfacAssert.setInterfacId(newId);
                }

                int code = casesInterfacAssertService.insertCasesInterfacAsserts(casesInterfacDto.getInterfacAsserts(), messageMap, request);

                if(code != ResCode.C0){
                    return ResCode.C1008;
                }
            }
            return ResCode.C0;

        }catch (NullPointerException e){
            LOGGER.error("insertCasesInterfacBasics 执行失败 {}", e.toString());
        }
        messageMap.put("errmsg", "插入接口信息失败");
        return ResCode.C1008;
    }

    /** 接口信息更改 */
    public int updateCasesInterfac(List<CasesInterfacDto> casesInterfacs, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} casesInterfac {}", ip, JSON.toJSONString(casesInterfacs));

        if(casesInterfacs == null || casesInterfacs.size() == 0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        for (CasesInterfac casesInterfac: casesInterfacs){
            if(!checkRequestInformation(casesInterfac)){
                messageMap.put("errmsg", "POST请求无请求主体");
                return ResCode.C1002;
            }

            casesInterfac.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));
            if(casesInterfacDao.updateCasesInterfacBasic(casesInterfac) != 1){
                messageMap.put("errmsg", "更改接口信息失败");
                return ResCode.C1008;
            }
        }
        return ResCode.C0;
    }

    /** 接口信息删除 */
    public int deleteCasesInterfacs(Integer id, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("delete ip {} ids {}", ip, id);

        if(id==null || id == 0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        int code = casesInterfacAssertService.deleteByCasesInterfacId(id, messageMap, request);
        if(code != ResCode.C0){
            return code;
        }
        if(casesInterfacDao.deleteCasesInterfacs(id) == 0){
            messageMap.put("errmsg", "删除接口信息失败");
            return ResCode.C1008;
        }

        return ResCode.C0;

    }

    /** 通过用例id接口信息删除 */
    public int deleteByCasesId(Integer[] casesBasicIds, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} casesBasicId {}", ip, casesBasicIds);
        for(Integer casesBasicId: casesBasicIds){
            List<CasesInterfacDto> casesInterfacDtos = casesInterfacDao.getCasesInterfacByCaeseId(casesBasicId);
            if(casesInterfacDtos == null || casesInterfacDtos.size() == 0){
                return ResCode.C0;
            }
            for(CasesInterfacDto casesInterfacDto: casesInterfacDtos){
                int code = casesInterfacAssertService.deleteByCasesInterfacId(casesInterfacDto.getId(), messageMap, request);
                if(code != ResCode.C0){
                    return code;
                }
            }
            int actionNum = casesInterfacDao.deleteCasesInterfacByCaeseId(casesBasicId);
            if(actionNum == 0){
                messageMap.put("errmsg", "删除接口失败");
                return ResCode.C1008;
            }
        }

        return ResCode.C0;
    }

    /** 校验请求信息内容是否合法 */
    private static boolean checkRequestInformation(CasesInterfac casesInterfac){
        if(!casesInterfac.getMethodType().equals(GloryRoadEnum.CaseSubMethod.GET)
                && casesInterfac.getBodyType() == null){
            return false;
        }
        if(casesInterfac.getStepNum() == null){
            return false;
        }
        return true;

    }
}
