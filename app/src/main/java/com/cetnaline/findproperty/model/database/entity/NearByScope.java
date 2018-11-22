package com.cetnaline.findproperty.model.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(
        nameInDb = "centanet_nearby_scope"
)
public class NearByScope {
    @Id
    private Long id;
    private Integer GScopeId;
    private String GScopeIdStr;
    private String GScopeCnName;
    private String FullPY;
    private String FirstPY;
    private Integer GScopeLevel;
    private Integer ParentId;
    private Double Lng;
    private Double Lat;



    @Generated(hash = 274442690)
    public NearByScope(Long id, Integer GScopeId, String GScopeIdStr,
                       String GScopeCnName, String FullPY, String FirstPY, Integer GScopeLevel,
                       Integer ParentId, Double Lng, Double Lat) {
        this.id = id;
        this.GScopeId = GScopeId;
        this.GScopeIdStr = GScopeIdStr;
        this.GScopeCnName = GScopeCnName;
        this.FullPY = FullPY;
        this.FirstPY = FirstPY;
        this.GScopeLevel = GScopeLevel;
        this.ParentId = ParentId;
        this.Lng = Lng;
        this.Lat = Lat;
    }

    @Generated(hash = 283655608)
    public NearByScope() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGScopeId() {
        return GScopeId;
    }

    public void setGScopeId(Integer GScopeId) {
        this.GScopeId = GScopeId;
    }

    public String getGScopeCnName() {
        return GScopeCnName;
    }

    public void setGScopeCnName(String GScopeCnName) {
        this.GScopeCnName = GScopeCnName;
    }

    public String getFullPY() {
        return FullPY;
    }

    public void setFullPY(String fullPY) {
        FullPY = fullPY;
    }

    public String getFirstPY() {
        return FirstPY;
    }

    public void setFirstPY(String firstPY) {
        FirstPY = firstPY;
    }

    public Integer getGScopeLevel() {
        return GScopeLevel;
    }

    public void setGScopeLevel(Integer GScopeLevel) {
        this.GScopeLevel = GScopeLevel;
    }

    public Integer getParentId() {
        return ParentId;
    }

    public void setParentId(Integer parentId) {
        ParentId = parentId;
    }

    public Double getLng() {
        return Lng;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public String getGScopeIdStr() {
        return GScopeIdStr;
    }

    public void setGScopeIdStr(String GScopeIdStr) {
        this.GScopeIdStr = GScopeIdStr;
    }
}
