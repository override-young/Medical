package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class CompanyWarningEntity implements Serializable {

    /**
     * pageIndex : 1
     * pageSize : 1
     * totalCount : 1
     * totalPages : 1
     * items : [{"organizationId":"7e4f2729-fa3b-412a-8088-9d7827370a87",
     * "organizationName":"开发供应商","organizationTel":"18696728320","currentDate":"2019-12-18
     * 16:45:17","businessLicenseDate":"2019-09-30 00:00:00",
     * "marketingAuthorizationDate":"2019-08-31 00:00:00","businessCertificateDate":null}]
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
         * organizationId : 7e4f2729-fa3b-412a-8088-9d7827370a87
         * organizationName : 开发供应商
         * organizationTel : 18696728320
         * currentDate : 2019-12-18 16:45:17
         * businessLicenseDate : 2019-09-30 00:00:00
         * marketingAuthorizationDate : 2019-08-31 00:00:00
         * businessCertificateDate : null
         */

        private String organizationId;
        private String organizationName;
        private String organizationTel;
        private String currentDate;
        private String businessLicenseDate;
        private String marketingAuthorizationDate;
        private String businessCertificateDate;

        public String getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getOrganizationTel() {
            return organizationTel;
        }

        public void setOrganizationTel(String organizationTel) {
            this.organizationTel = organizationTel;
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public String getBusinessLicenseDate() {
            return businessLicenseDate;
        }

        public void setBusinessLicenseDate(String businessLicenseDate) {
            this.businessLicenseDate = businessLicenseDate;
        }

        public String getMarketingAuthorizationDate() {
            return marketingAuthorizationDate;
        }

        public void setMarketingAuthorizationDate(String marketingAuthorizationDate) {
            this.marketingAuthorizationDate = marketingAuthorizationDate;
        }

        public String getBusinessCertificateDate() {
            return businessCertificateDate;
        }

        public void setBusinessCertificateDate(String businessCertificateDate) {
            this.businessCertificateDate = businessCertificateDate;
        }
    }
}
