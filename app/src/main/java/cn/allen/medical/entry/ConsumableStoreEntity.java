package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class ConsumableStoreEntity implements Serializable {

    /**
     * pageIndex : 1
     * pageSize : 1
     * totalCount : 1
     * totalPages : 1
     * items : [{"currentDate":"2019-12-18 15:26:22","productName":"碧螺春","depotName":"CK_001",
     * "batchNo":"789","bornDate":"2019-09-06 00:00:00","expireDate":"2019-09-30 00:00:00",
     * "pUnit":"单位","pSpec":"规格"}]
     */

    private int pageIndex;
    private int pageSize;
    private int totalCount;
    private int totalPages;
    private List<ItemsBean> items;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * currentDate : 2019-12-18 15:26:22
         * productName : 碧螺春
         * depotName : CK_001
         * batchNo : 789
         * bornDate : 2019-09-06 00:00:00
         * expireDate : 2019-09-30 00:00:00
         * pUnit : 单位
         * pSpec : 规格
         */

        private String currentDate;
        private String productName;
        private String depotName;
        private String batchNo;
        private String bornDate;
        private String expireDate;
        private String pUnit;
        private String pSpec;

        public String getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getDepotName() {
            return depotName;
        }

        public void setDepotName(String depotName) {
            this.depotName = depotName;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public String getBornDate() {
            return bornDate;
        }

        public void setBornDate(String bornDate) {
            this.bornDate = bornDate;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
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
    }
}
