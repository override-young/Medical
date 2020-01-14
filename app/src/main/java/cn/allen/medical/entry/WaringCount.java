package cn.allen.medical.entry;

import java.io.Serializable;

public class WaringCount implements Serializable {
    private int enterpriseCertWarningCount;//企业资质预警数量
    private int productCertWarningCount;//产品资质预警数量
    private int stockWarningCount;//库存效期预警数量
    private int contractWarningCount;//合同效期预警数量
    private int totalCount;//总数量
    private int selfCertCount;//供应商企业资质预警数量
    private int orgCertCount;//供应商厂商资质预警数量
    private int productCertCount;//供应商耗材资质预警数量
    private int authCertCount;//供应商代理授权书预警数量
    private int contractCount;//供应商合同效期预警数量

    public WaringCount() {
    }

    public int getEnterpriseCertWarningCount() {
        return enterpriseCertWarningCount;
    }

    public void setEnterpriseCertWarningCount(int enterpriseCertWarningCount) {
        this.enterpriseCertWarningCount = enterpriseCertWarningCount;
    }

    public int getProductCertWarningCount() {
        return productCertWarningCount;
    }

    public void setProductCertWarningCount(int productCertWarningCount) {
        this.productCertWarningCount = productCertWarningCount;
    }

    public int getStockWarningCount() {
        return stockWarningCount;
    }

    public void setStockWarningCount(int stockWarningCount) {
        this.stockWarningCount = stockWarningCount;
    }

    public int getContractWarningCount() {
        return contractWarningCount;
    }

    public void setContractWarningCount(int contractWarningCount) {
        this.contractWarningCount = contractWarningCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getSelfCertCount() {
        return selfCertCount;
    }

    public void setSelfCertCount(int selfCertCount) {
        this.selfCertCount = selfCertCount;
    }

    public int getOrgCertCount() {
        return orgCertCount;
    }

    public void setOrgCertCount(int orgCertCount) {
        this.orgCertCount = orgCertCount;
    }

    public int getProductCertCount() {
        return productCertCount;
    }

    public void setProductCertCount(int productCertCount) {
        this.productCertCount = productCertCount;
    }

    public int getAuthCertCount() {
        return authCertCount;
    }

    public void setAuthCertCount(int authCertCount) {
        this.authCertCount = authCertCount;
    }

    public int getContractCount() {
        return contractCount;
    }

    public void setContractCount(int contractCount) {
        this.contractCount = contractCount;
    }
}
