package com.cetnaline.findproperty.model.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(
        nameInDb = "centanet_school"
)
public class School {

    /**
     * SchoolId : 437
     * SchoolName : 上海嘉定区马陆智慧幼儿园
     * GScopeId : 217808
     * GScopeName : 嘉定城区
     * RegionId : 2178
     * RegionName : 嘉定
     * SchoolType : 幼儿园
     * SchoolGrade : 一级师范
     * SchoolFeature : 童谣教育
     * SchoolShortName : 马陆智慧幼儿园
     * SaleNumber : 0
     * RentNumber : 0
     * MaxSalePrice : 0
     * MinSalePrice : 0
     * MaxUnitSalePrice : 0
     * MinUnitSalePrice : 0
     * MaxRentPrice : 0
     * MinRentPrice : 0
     * Address : 嘉戬支路285号
     * Lng : 121.305802
     * Lat : 31.380243
     * Phone : 021-59511005
     * Remark :
     * ShoolImgUrl : https://imgsh.centanet.com/shanghai/staticfile/schoolimg/437/0.jpg
     * AvgPrice : 0
     * PinYin : MLZHYEY
     * SchoolToEstateInfo : {"EstateTotal":0,"SchoolId":0,"Estates":[]}
     */

    @Id
    private Long id;
    private Integer SchoolId;
    private String SchoolName;
    private Integer GScopeId;
    private String GScopeName;
    private Integer RegionId;
    private String RegionName;
    private String SchoolType;
    private String SchoolGrade;
    private String SchoolFeature;
    private String SchoolShortName;
    private String Address;
    private Double Lng;
    private Double Lat;
    private String Phone;
    private String Remark;
    private String ShoolImgUrl;
    private String PinYin;

    @Generated(hash = 1269365546)
    public School(Long id, Integer SchoolId, String SchoolName, Integer GScopeId,
            String GScopeName, Integer RegionId, String RegionName, String SchoolType,
            String SchoolGrade, String SchoolFeature, String SchoolShortName, String Address,
            Double Lng, Double Lat, String Phone, String Remark, String ShoolImgUrl,
            String PinYin) {
        this.id = id;
        this.SchoolId = SchoolId;
        this.SchoolName = SchoolName;
        this.GScopeId = GScopeId;
        this.GScopeName = GScopeName;
        this.RegionId = RegionId;
        this.RegionName = RegionName;
        this.SchoolType = SchoolType;
        this.SchoolGrade = SchoolGrade;
        this.SchoolFeature = SchoolFeature;
        this.SchoolShortName = SchoolShortName;
        this.Address = Address;
        this.Lng = Lng;
        this.Lat = Lat;
        this.Phone = Phone;
        this.Remark = Remark;
        this.ShoolImgUrl = ShoolImgUrl;
        this.PinYin = PinYin;
    }

    @Generated(hash = 1579966795)
    public School() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(Integer schoolId) {
        SchoolId = schoolId;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public Integer getGScopeId() {
        return GScopeId;
    }

    public void setGScopeId(Integer GScopeId) {
        this.GScopeId = GScopeId;
    }

    public String getGScopeName() {
        return GScopeName;
    }

    public void setGScopeName(String GScopeName) {
        this.GScopeName = GScopeName;
    }

    public Integer getRegionId() {
        return RegionId;
    }

    public void setRegionId(Integer regionId) {
        RegionId = regionId;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
    }

    public String getSchoolType() {
        return SchoolType;
    }

    public void setSchoolType(String schoolType) {
        SchoolType = schoolType;
    }

    public String getSchoolGrade() {
        return SchoolGrade;
    }

    public void setSchoolGrade(String schoolGrade) {
        SchoolGrade = schoolGrade;
    }

    public String getSchoolFeature() {
        return SchoolFeature;
    }

    public void setSchoolFeature(String schoolFeature) {
        SchoolFeature = schoolFeature;
    }

    public String getSchoolShortName() {
        return SchoolShortName;
    }

    public void setSchoolShortName(String schoolShortName) {
        SchoolShortName = schoolShortName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getShoolImgUrl() {
        return ShoolImgUrl;
    }

    public void setShoolImgUrl(String shoolImgUrl) {
        ShoolImgUrl = shoolImgUrl;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String pinYin) {
        PinYin = pinYin;
    }
}
