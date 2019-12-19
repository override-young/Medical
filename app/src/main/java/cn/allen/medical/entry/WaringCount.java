package cn.allen.medical.entry;

import java.io.Serializable;

public class WaringCount implements Serializable {
    private int enterpriseCertWarningCount;//企业资质预警数量
    private int productCertWarningCount;//产品资质预警数量
    private int stockWarningCount;//库存效期预警数量
    private int contractWarningCount;//合同效期预警数量
    private int totalCount;//总数量

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
}
