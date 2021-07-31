package com.gloryroad.demo.dto.cases;

import com.gloryroad.demo.entity.cases.CasesInterfac;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;

import java.util.List;

public class CasesInterfacDto extends CasesInterfac {
    private List<CasesInterfacAssert> interfacAsserts;

    public List<CasesInterfacAssert> getInterfacAsserts() {
        return interfacAsserts;
    }

    public void setInterfacAsserts(List<CasesInterfacAssert> interfacAsserts) {
        this.interfacAsserts = interfacAsserts;
    }
}
