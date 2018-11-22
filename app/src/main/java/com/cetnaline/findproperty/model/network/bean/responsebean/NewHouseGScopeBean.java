package com.cetnaline.findproperty.model.network.bean.responsebean;

import com.cetnaline.findproperty.model.database.entity.GScope;

import java.util.List;

public class NewHouseGScopeBean {
    private int DistrictId;
    private int NewPropCount;
    private GScope District;
    private List<NewHouseGScopeBean> GscopeEsts;

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int districtId) {
        DistrictId = districtId;
    }

    public int getNewPropCount() {
        return NewPropCount;
    }

    public void setNewPropCount(int newPropCount) {
        NewPropCount = newPropCount;
    }

    public GScope getDistrict() {
        return District;
    }

    public void setDistrict(GScope district) {
        District = district;
    }

    public List<NewHouseGScopeBean> getGscopeEsts() {
        return GscopeEsts;
    }

    public void setGscopeEsts(List<NewHouseGScopeBean> gscopeEsts) {
        GscopeEsts = gscopeEsts;
    }
}
