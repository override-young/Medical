package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class ToDoGysEntity implements Serializable {

    /**
     * pageIndex : 0
     * pageSize : 20
     * totalCount : 1
     * totalPages : 1
     * items : [{"id":"a0aac124-a47b-4c1b-b92a-77c2958f1316",
     * "orgId":"0dcef36b-052b-4a8f-abf6-c98a71e50ac1","orgName":"开发配送商",
     * "orderNo":"PR19103000110","status":15,"recipientName":"张三"}]
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
         * id : a0aac124-a47b-4c1b-b92a-77c2958f1316
         * orgId : 0dcef36b-052b-4a8f-abf6-c98a71e50ac1
         * orgName : 开发配送商
         * orderNo : PR19103000110
         * status : 15
         * recipientName : 张三
         */

        private String id;
        private String orgId;
        private String orgName;
        private String orderNo;
        private int status;
        private String recipientName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRecipientName() {
            return recipientName;
        }

        public void setRecipientName(String recipientName) {
            this.recipientName = recipientName;
        }
    }
}
