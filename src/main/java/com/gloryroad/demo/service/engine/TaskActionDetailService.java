package com.gloryroad.demo.service.engine;

import com.gloryroad.demo.entity.interfac.InterfacAssert;
import com.gloryroad.demo.service.cases.CasesBasicService;
import com.gloryroad.demo.utils.GeneralRedis;
import com.gloryroad.demo.utils.IpUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TaskActionDetailService {

    @Autowired
    private GeneralRedis generalRedis;

    @Autowired
    private CasesBasicService casesBasicService;

    /** 接口断言信息查找 */
    public List<InterfacAssert> getTaskAction(List<Integer> casesIds, HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        casesBasicService
    }
}
