package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class ConsumableQualityEntity implements Serializable {

    /**
     * pageIndex : 1
     * pageSize : 1
     * totalCount : 1
     * totalPages : 1
     * items : [{"currentDate":"2019-12-18 16:28:25",
     * "productId":"cf2f1d06-bd1b-4341-8cad-991fdcf01f51","productName":"心相印纸巾",
     * "productUnit":"单位","productSpec":"规格","supplierId":"7e4f2729-fa3b-412a-8088-9d7827370a87",
     * "supplierName":"开发供应商","recordCardDate":"2019-08-29 00:00:00",
     * "letterOfAuthorizationName":"","letterOfAuthorizationDate":null,"shortName":""}]
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
         * currentDate : 2019-12-18 16:28:25
         * productId : cf2f1d06-bd1b-4341-8cad-991fdcf01f51
         * productName : 心相印纸巾
         * productUnit : 单位
         * productSpec : 规格
         * supplierId : 7e4f2729-fa3b-412a-8088-9d7827370a87
         * supplierName : 开发供应商
         * recordCardDate : 2019-08-29 00:00:00
         * letterOfAuthorizationName :
         * letterOfAuthorizationDate : null
         * shortName :
         */

        private String currentDate;
        private String productId;
        private String productName;
        private String productUnit;
        private String productSpec;
        private String supplierId;
        private String supplierName;
        private String recordCardDate;
        private String letterOfAuthorizationName;
        private Object letterOfAuthorizationDate;
        private String shortName;

        public String getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductUnit() {
            return productUnit;
        }

        public void setProductUnit(String productUnit) {
            this.productUnit = productUnit;
        }

        public String getProductSpec() {
            return productSpec;
        }

        public void setProductSpec(String productSpec) {
            this.productSpec = productSpec;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getRecordCardDate() {
            return recordCardDate;
        }

        public void setRecordCardDate(String recordCardDate) {
            this.recordCardDate = recordCardDate;
        }

        public String getLetterOfAuthorizationName() {
            return letterOfAuthorizationName;
        }

        public void setLetterOfAuthorizationName(String letterOfAuthorizationName) {
            this.letterOfAuthorizationName = letterOfAuthorizationName;
        }

        public Object getLetterOfAuthorizationDate() {
            return letterOfAuthorizationDate;
        }

        public void setLetterOfAuthorizationDate(Object letterOfAuthorizationDate) {
            this.letterOfAuthorizationDate = letterOfAuthorizationDate;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
    }
}
