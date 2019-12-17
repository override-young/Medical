package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class ToDoContractEntity implements Serializable {

    /**
     * pageIndex : 0
     * pageSize : 20
     * totalCount : 2
     * totalPages : 1
     * items : [{"id":"959e9951-c694-49fa-992e-5702e0ac6bfa","partyAName":"开发配送商",
     * "contractNo":"DH20190904","status":5},{"id":"52d7a1f4-4729-4218-bbb1-50c2aee9ee29",
     * "partyAName":"开发配送商","contractNo":"3456634546","status":5}]
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
         * id : 959e9951-c694-49fa-992e-5702e0ac6bfa
         * partyAName : 开发配送商
         * contractNo : DH20190904
         * status : 5
         */

        private String id;
        private String partyAName;
        private String contractNo;
        private int status;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    @Override
    public String toString() {
        return "ToDoContractEntity{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                ", items=" + items +
                '}';
    }
}
