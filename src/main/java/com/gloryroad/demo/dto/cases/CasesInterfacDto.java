package com.gloryroad.demo.dto.cases;

import com.gloryroad.demo.entity.cases.CasesInterfac;
import com.gloryroad.demo.entity.cases.CasesInterfacAssert;

import java.util.List;

public class CasesInterfacDto extends CasesInterfac {
    private List<CasesInterfacAssert> casesInterfacAsserts;

    public List<CasesInterfacAssert> getCasesInterfacAsserts() {
        return casesInterfacAsserts;
    }

    public void setCasesInterfacAsserts(List<CasesInterfacAssert> casesInterfacAsserts) {
        this.casesInterfacAsserts = casesInterfacAsserts;
    }
}
