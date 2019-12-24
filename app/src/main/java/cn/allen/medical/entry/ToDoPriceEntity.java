package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class ToDoPriceEntity implements Serializable {

    /**
     * pageIndex : 1
     * pageSize : 0
     * totalCount : 1
     * totalPages : 1
     * items : [{"id":"30dfc6f1-9724-4588-b956-4c0b50115ade","orgName":"开发供应商","status":1,
     * "createTime":"2019-12-11 09:36:36"}]
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
         * id : 30dfc6f1-9724-4588-b956-4c0b50115ade
         * orgName : 开发供应商
         * status : 1
         * createTime : 2019-12-11 09:36:36
         */

        private String id;
        private String orgName;
        private int status;
        private String createTime;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
