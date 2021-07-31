package com.gloryroad.demo.service.cases;

import com.alibaba.fastjson.JSON;
import com.gloryroad.demo.Vo.PageModel;
import com.gloryroad.demo.Vo.cases.CasesBasicQueryVo;
import com.gloryroad.demo.Vo.interfac.InterfacBasicQueryVo;
import com.gloryroad.demo.constant.GloryRoadEnum;
import com.gloryroad.demo.constant.ResCode;
import com.gloryroad.demo.dao.cases.CasesBasicDao;
import com.gloryroad.demo.dao.interfac.InterfacBasicDao;
import com.gloryroad.demo.dto.cases.CasesBasicDto;
import com.gloryroad.demo.dto.cases.CasesInterfacDto;
import com.gloryroad.demo.dto.interfac.InterfacBasicDto;
import com.gloryroad.demo.entity.cases.CasesBasic;
import com.gloryroad.demo.entity.cases.CasesInterfac;
import com.gloryroad.demo.entity.interfac.InterfacBasic;
import com.gloryroad.demo.entity.session.IUser;
import com.gloryroad.demo.service.interfac.InterfacAssertService;
import com.gloryroad.demo.service.system.SystemGroupService;
import com.gloryroad.demo.utils.IpUtil;
import com.gloryroad.demo.utils.StringUtil;
import com.gloryroad.demo.utils.TimesUtil;
import com.gloryroad.demo.utils.session.UserUtil;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class CasesBasicService {

    @Autowired
    private CasesBasicDao casesBasicDao;
    
    @Autowired
    private SystemGroupService systemGroupService;

    @Autowired
    private CasesInterfacService casesInterfacService;

    @Autowired
    private UserUtil userUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(CasesBasicService.class);

    /** 用例信息查找 */
    public PageModel<CasesBasicDto> findCasesBasics(CasesBasicQueryVo casesBasicQueryVo, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("find ip {} casesBasicQueryVo {}", ip, JSON.toJSONString(casesBasicQueryVo));
        List<CasesBasicDto> casesBasicDtos;
        PageModel<CasesBasicDto> page = new PageModel();
        if(casesBasicQueryVo.getId() != null){
            casesBasicDtos = casesBasicDao.getCasesBasicById(casesBasicQueryVo.getId());
        }else {
            casesBasicDtos = casesBasicDao.getCasesBasics(casesBasicQueryVo);
        }

        for(CasesBasicDto casesBasicDto: casesBasicDtos){
            casesBasicDto.setGroupName(systemGroupService.findSystemGroupById(casesBasicDto.getGroupId()).getGroupName());
            casesBasicDto.setCasesInterfacDtos(casesInterfacService.findCasesInterfacsByCasesId(casesBasicDto.getId()));
        }

        page.setResult(casesBasicDtos);
        return page;
    }

    /** 用例信息查找 */
    public List<CasesBasicDto> findCasesBasicDtos(List<String> casesIds){
        List<CasesBasicDto> casesBasicDtos = Lists.newArrayList();
        if(casesIds == null || casesIds.size() == 0) {
            return casesBasicDtos;
        }

        for(String casesId: casesIds){
            casesBasicDtos.addAll(casesBasicDao.getCasesBasicById(casesId));
        }

        for(CasesBasicDto casesBasicDto: casesBasicDtos){
            casesBasicDto.setGroupName(systemGroupService.findSystemGroupById(casesBasicDto.getGroupId()).getGroupName());
            casesBasicDto.setCasesInterfacDtos(casesInterfacService.findCasesInterfacsByCasesId(casesBasicDto.getId()));
        }

        return casesBasicDtos;
    }

    /** 用例信息查找 */
    public List<CasesBasic> findCasesBasics(List<String> casesIds){
        List<CasesBasic> casesBasicList = Lists.newArrayList();
        if(casesIds == null || casesIds.size() == 0) {
            return casesBasicList;
        }

        for(String casesId: casesIds){
            if(StringUtil.isNotBlank(casesId)) {
                casesBasicList.addAll(casesBasicDao.getCasesBasicById(casesId));
            }
        }

        return casesBasicList;
    }

    /** 用例信息插入 */
    public int insertCasesBasic(CasesBasic casesBasic, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} casesBasic {}", ip, JSON.toJSONString(casesBasic));
        IUser user = userUtil.getUserSession(request);

        if(casesBasic == null
                || casesBasic.getCaseName() == null
                || casesBasic.getGroupId() == null)
        {
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        casesBasic.setCreateAccount(user.getAccount());
        casesBasic.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));

        if(casesBasicDao.insertCasesBasic(casesBasic) != 0){
            return ResCode.C0;
        };
        messageMap.put("errmsg", "插入用例信息失败");
        return ResCode.C1008;
    }

    /** 用例信息拷贝 */
    public int copyCasesBasic(String id, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("insert ip {} CasesId {}", ip, id);
        int newId;
        if(id == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        List<CasesBasicDto> casesBasicDtos = casesBasicDao.getCasesBasicById(id);
        if(casesBasicDtos.size() == 0){
            messageMap.put("errmsg", id+"用例不存在");
            return ResCode.C1001;
        }

        CasesBasicDto casesBasicDto = casesBasicDtos.get(0);
        casesBasicDto.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));
        try{
            newId = casesBasicDao.insertCasesBasic(casesBasicDto);
        }catch (NullPointerException e){
            messageMap.put("errmsg", "拷贝用例信息失败");
            return ResCode.C1008;
        }


        List<CasesInterfacDto> casesInterfacDtos = casesInterfacService.findCasesInterfacsByCasesId(casesBasicDto.getId());
        for(CasesInterfacDto casesInterfacDto: casesInterfacDtos){
            casesInterfacDto.setCasesId(newId);
        }
        int code = casesInterfacService.insertCasesInterfacs(casesInterfacDtos, messageMap, request);
        if(code == ResCode.C0){
            return ResCode.C0;
        }
        return code;
    }

    /** 用例信息更改 */
    public int updateCasesBasic(CasesBasic casesBasic, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("update ip {} casesBasic {}", ip, JSON.toJSONString(casesBasic));

        if(casesBasic == null || casesBasic.getId() == null){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        casesBasic.setCreateTime(TimesUtil.millisecondToSecond(System.currentTimeMillis()));
        if(casesBasicDao.updateCasesBasic(casesBasic) == 1){
            return ResCode.C0;
        }
        messageMap.put("errmsg", "更改用例信息失败");
        return ResCode.C1008;
    }

    /** 用例信息删除 */
    public int deleteCasesBasics(Integer[] ids, Map<String, String> messageMap, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        LOGGER.info("delete ip {} ids {}", ip, JSON.toJSONString(ids));

        if(ids==null || ids.length==0){
            messageMap.put("errmsg", "参数缺失");
            return ResCode.C1001;
        }
        int code = casesInterfacService.deleteByCasesId(ids, messageMap, request);
        if(code != ResCode.C0){
            return code;
        }

        if(casesBasicDao.deleteCasesBasics(ids) == 0){
            messageMap.put("errmsg", "删除用例信息失败");
            return ResCode.C1008;
        }

        return ResCode.C0;
    }
}
