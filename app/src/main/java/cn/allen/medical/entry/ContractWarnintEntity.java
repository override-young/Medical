package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class ContractWarnintEntity implements Serializable {

    /**
     * pageIndex : 1
     * pageSize : 0
     * totalCount : 1
     * totalPages : 1
     * items : [{"organizationId":"bbb276a0-747f-469a-8181-3cf3d377f2ba",
     * "organizationName":"开发院内配送","organizationTel":"15696120902","contactsName":"麻六",
     * "contractNo":"345654","currentDate":"2019-12-18 11:44:24","startDate":"2019-10-11
     * 00:00:00","expiredDate":"2019-10-31 00:00:00"}]
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
         * organizationId : bbb276a0-747f-469a-8181-3cf3d377f2ba
         * organizationName : 开发院内配送
         * organizationTel : 15696120902
         * contactsName : 麻六
         * contractNo : 345654
         * currentDate : 2019-12-18 11:44:24
         * startDate : 2019-10-11 00:00:00
         * expiredDate : 2019-10-31 00:00:00
         */

        private String organizationId;
        private String organizationName;
        private String organizationTel;
        private String contactsName;
        private String contractNo;
        private String currentDate;
        private String startDate;
        private String expiredDate;

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

        public String getContactsName() {
            return contactsName;
        }

        public void setContactsName(String contactsName) {
            this.contactsName = contactsName;
        }

        public String getContractNo() {
            return contractNo;
        }

        public void setContractNo(String contractNo) {
            this.contractNo = contractNo;
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(String expiredDate) {
            this.expiredDate = expiredDate;
        }
    }
}
