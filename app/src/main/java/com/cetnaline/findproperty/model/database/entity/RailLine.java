package com.cetnaline.findproperty.model.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity(
        nameInDb = "centanet_railline"
)
public class RailLine {

    private Integer RailLineID;
    private String RailLineName;
    private Integer OrderBy;

    public Integer getRailLineID() {
        return this.RailLineID;
    }
    public void setRailLineID(Integer RailLineID) {
        this.RailLineID = RailLineID;
    }
    public String getRailLineName() {
        return this.RailLineName;
    }
    public void setRailLineName(String RailLineName) {
        this.RailLineName = RailLineName;
    }
    public Integer getOrderBy() {
        return this.OrderBy;
    }
    public void setOrderBy(Integer OrderBy) {
        this.OrderBy = OrderBy;
    }

    @Transient
    private List<RailWay> RailWayList;

    @Generated(hash = 1250898914)
    public RailLine(Integer RailLineID, String RailLineName, Integer OrderBy) {
        this.RailLineID = RailLineID;
        this.RailLineName = RailLineName;
        this.OrderBy = OrderBy;
    }
    @Generated(hash = 1258454533)
    public RailLine() {
    }

    public List<RailWay> getRailWayList() {
        return RailWayList;
    }

    public void setRailWayList(List<RailWay> railWayList) {
        RailWayList = railWayList;
    }

}