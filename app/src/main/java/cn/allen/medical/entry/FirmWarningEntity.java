package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class FirmWarningEntity implements Serializable {

    /**
     * pageIndex : 0
     * pageSize : 20
     * totalCount : 1
     * totalPages : 1
     * items : [{"id":"0dcef36b-052b-4a8f-abf6-c98a71e50ac1","organizationName":"开发配送商",
     * "agentName":"耗材代理商001","certList":[{"currentDate":"2020-01-03 15:25:04",
     * "certName":"销售代表授权书","expireDate":"2019-08-25 00:00:00"}]}]
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
         * id : 0dcef36b-052b-4a8f-abf6-c98a71e50ac1
         * organizationName : 开发配送商
         * agentName : 耗材代理商001
         * certList : [{"currentDate":"2020-01-03 15:25:04","certName":"销售代表授权书",
         * "expireDate":"2019-08-25 00:00:00"}]
         */

        private String id;
        private String organizationName;
        private String agentName;
        private List<CertListBean> certList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrganizationName() {
            return organizationName;
        }

        public void setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public List<CertListBean> getCertList() {
            return certList;
        }

        public void setCertList(List<CertListBean> certList) {
            this.certList = certList;
        }

        public static class CertListBean {
            /**
             * currentDate : 2020-01-03 15:25:04
             * certName : 销售代表授权书
             * expireDate : 2019-08-25 00:00:00
             */

            private String currentDate;
            private String certName;
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

            public String getExpireDate() {
                return expireDate;
            }

            public void setExpireDate(String expireDate) {
                this.expireDate = expireDate;
            }
        }
    }
}
