package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class GysProxyEntity implements Serializable {

    /**
     * pageIndex : 0
     * pageSize : 20
     * totalCount : 3
     * totalPages : 1
     * items : [{"currentDate":"2020-01-03 15:52:06","certName":"哈哈哈",
     * "certificateCode":"SQS321554364346","expireDate":"2019-10-31 00:00:00"}]
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
         * currentDate : 2020-01-03 15:52:06
         * certName : 哈哈哈
         * certificateCode : SQS321554364346
         * expireDate : 2019-10-31 00:00:00
         */

        private String currentDate;
        private String certName;
        private String certificateCode;
        private String expireDate;

        public String getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public String getCertName() {
            return certName;
        }

        public void setCertName(String certName) {
            this.certName = certName;
        }

        public String getCertificateCode() {
            return certificateCode;
        }

        public void setCertificateCode(String certificateCode) {
            this.certificateCode = certificateCode;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }
    }
}
