package cn.allen.medical.entry;

import java.io.Serializable;

public class PriceDetailsEntity implements Serializable {

    /**
     * id : 30dfc6f1-9724-4588-b956-4c0b50115ade
     * orgName : 开发供应商
     * pName : 配药注射器x
     * pUnit : 只
     * pSpec : 只
     * price : 30
     * priceStartTime : 2019-05-01 00:00:00
     * annexUrl :
     * status : 1
     * prePrice : 59
     * createTime : 2019-12-11 09:36:36
     */

    private String id;
    private String orgName;
    private String pName;
    private String pUnit;
    private String pSpec;
    private double price;
    private String priceStartTime;
    private String annexUrl;
    private int status;
    private double prePrice;
    private String createTime;
    /**
     * shortName :
     */

    private String shortName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPUnit() {
        return pUnit;
    }

    public void setPUnit(String pUnit) {
        this.pUnit = pUnit;
    }

    public String getPSpec() {
        return pSpec;
    }

    public void setPSpec(String pSpec) {
        this.pSpec = pSpec;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPriceStartTime() {
        return priceStartTime;
    }

    public void setPriceStartTime(String priceStartTime) {
        this.priceStartTime = priceStartTime;
    }

    public String getAnnexUrl() {
        return annexUrl;
    }

    public void setAnnexUrl(String annexUrl) {
        this.annexUrl = annexUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(double prePrice) {
        this.prePrice = prePrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
