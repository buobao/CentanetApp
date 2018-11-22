package com.cetnaline.findproperty.model.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "centanet_gscope")
public class GScope {
    @Id
    private Integer GScopeId;
    private String GScopeCode;
    private Integer GScopeLevel;
    private String GScopeName;
    private String GScopeAlias;
    private String FullPY;
    private String FirstPY;
    private Integer ParentId;
    private Integer OrderBy;
    private Double Lng;
    private Double Lat;
    private Integer SaleCount;
    private Integer RentCount;
    private Integer EstateCount;
    private Double SaleAvgPrice;
    private Double SaleAvgPriceRise;
    private Integer PostCount;

    @Generated(hash = 503759470)
    public GScope(Integer GScopeId, String GScopeCode, Integer GScopeLevel,
            String GScopeName, String GScopeAlias, String FullPY, String FirstPY,
            Integer ParentId, Integer OrderBy, Double Lng, Double Lat,
            Integer SaleCount, Integer RentCount, Integer EstateCount,
            Double SaleAvgPrice, Double SaleAvgPriceRise, Integer PostCount) {
        this.GScopeId = GScopeId;
        this.GScopeCode = GScopeCode;
        this.GScopeLevel = GScopeLevel;
        this.GScopeName = GScopeName;
        this.GScopeAlias = GScopeAlias;
        this.FullPY = FullPY;
        this.FirstPY = FirstPY;
        this.ParentId = ParentId;
        this.OrderBy = OrderBy;
        this.Lng = Lng;
        this.Lat = Lat;
        this.SaleCount = SaleCount;
        this.RentCount = RentCount;
        this.EstateCount = EstateCount;
        this.SaleAvgPrice = SaleAvgPrice;
        this.SaleAvgPriceRise = SaleAvgPriceRise;
        this.PostCount = PostCount;
    }

    @Generated(hash = 1484189623)
    public GScope() {
    }

    public Integer getGScopeId() {
        return GScopeId;
    }

    public void setGScopeId(Integer GScopeId) {
        this.GScopeId = GScopeId;
    }

    public String getGScopeCode() {
        return GScopeCode;
    }

    public void setGScopeCode(String GScopeCode) {
        this.GScopeCode = GScopeCode;
    }

    public Integer getGScopeLevel() {
        return GScopeLevel;
    }

    public void setGScopeLevel(Integer GScopeLevel) {
        this.GScopeLevel = GScopeLevel;
    }

    public String getGScopeName() {
        return GScopeName;
    }

    public void setGScopeName(String GScopeName) {
        this.GScopeName = GScopeName;
    }

    public String getGScopeAlias() {
        return GScopeAlias;
    }

    public void setGScopeAlias(String GScopeAlias) {
        this.GScopeAlias = GScopeAlias;
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

    public Integer getParentId() {
        return ParentId;
    }

    public void setParentId(Integer parentId) {
        ParentId = parentId;
    }

    public Integer getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(Integer orderBy) {
        OrderBy = orderBy;
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

    public Integer getSaleCount() {
        return SaleCount;
    }

    public void setSaleCount(Integer saleCount) {
        SaleCount = saleCount;
    }

    public Integer getRentCount() {
        return RentCount;
    }

    public void setRentCount(Integer rentCount) {
        RentCount = rentCount;
    }

    public Integer getEstateCount() {
        return EstateCount;
    }

    public void setEstateCount(Integer estateCount) {
        EstateCount = estateCount;
    }

    public Double getSaleAvgPrice() {
        return SaleAvgPrice;
    }

    public void setSaleAvgPrice(Double saleAvgPrice) {
        SaleAvgPrice = saleAvgPrice;
    }

    public Double getSaleAvgPriceRise() {
        return SaleAvgPriceRise;
    }

    public void setSaleAvgPriceRise(Double saleAvgPriceRise) {
        SaleAvgPriceRise = saleAvgPriceRise;
    }

    public Integer getPostCount() {
        return PostCount;
    }

    public void setPostCount(Integer postCount) {
        PostCount = postCount;
    }
}
