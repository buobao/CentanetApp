package com.cetnaline.findproperty.model.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Entity mapped to table "RAIL_WAY".
 */
@Entity(
        nameInDb = "centanet_railway"
)
public class RailWay {

    private Integer RailWayID;
    private Integer RailLineID;
    private String RailWayName;
    private Double Lng;
    private Double Lat;
    private String BranchNum;

    @Generated(hash = 142211119)
    public RailWay(Integer RailWayID, Integer RailLineID, String RailWayName,
            Double Lng, Double Lat, String BranchNum) {
        this.RailWayID = RailWayID;
        this.RailLineID = RailLineID;
        this.RailWayName = RailWayName;
        this.Lng = Lng;
        this.Lat = Lat;
        this.BranchNum = BranchNum;
    }
    @Generated(hash = 286028539)
    public RailWay() {
    }

    public Integer getRailWayID() {
        return this.RailWayID;
    }
    public void setRailWayID(Integer RailWayID) {
        this.RailWayID = RailWayID;
    }
    public Integer getRailLineID() {
        return this.RailLineID;
    }
    public void setRailLineID(Integer RailLineID) {
        this.RailLineID = RailLineID;
    }
    public String getRailWayName() {
        return this.RailWayName;
    }
    public void setRailWayName(String RailWayName) {
        this.RailWayName = RailWayName;
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

    public String getBranchNum() {
        return BranchNum;
    }

    public void setBranchNum(String branchNum) {
        BranchNum = branchNum;
    }
}
