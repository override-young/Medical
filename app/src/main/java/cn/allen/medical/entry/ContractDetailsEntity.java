package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class ContractDetailsEntity implements Serializable {

    /**
     * id : 37521502-fe91-4e4e-8736-49887aa61369
     * partyAName : 开发供应商
     * contractNo : 534534564
     * contractStartTime : 2019-12-13 00:00:00
     * contractStopTime : 2019-12-21 00:00:00
     * status : 5
     * remarks :
     * supplierProductList : [{"packageName":"屠龙刀","unit":"把","spec":"把"},{"packageName":"倚天剑",
     * "unit":"支","spec":"把"}]
     */

    private String id;
    private String partyAName;
    private String contractNo;
    private String contractStartTime;
    private String contractStopTime;
    private int status;
    private String remarks;
    private List<SupplierProductListBean> supplierProductList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPartyAName() {
        return partyAName;
    }

    public void setPartyAName(String partyAName) {
        this.partyAName = partyAName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(String contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public String getContractStopTime() {
        return contractStopTime;
    }

    public void setContractStopTime(String contractStopTime) {
        this.contractStopTime = contractStopTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<SupplierProductListBean> getSupplierProductList() {
        return supplierProductList;
    }

    public void setSupplierProductList(List<SupplierProductListBean> supplierProductList) {
        this.supplierProductList = supplierProductList;
    }

    public static class SupplierProductListBean {
        /**
         * packageName : 屠龙刀
         * unit : 把
         * spec : 把
         */

        private String packageName;
        private String unit;
        private String spec;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }
}
