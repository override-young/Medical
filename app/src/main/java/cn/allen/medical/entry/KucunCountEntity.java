package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class KucunCountEntity implements Serializable {

    /**
     * pageIndex : 0
     * pageSize : 1
     * totalCount : 1
     * totalPages : 1
     * items : [{"pName":"富士X光感蓝胶片","pCode":"","pUnit":"盒","pSpec":"100g","quantity":579}]
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
         * pName : 富士X光感蓝胶片
         * pCode :
         * pUnit : 盒
         * pSpec : 100g
         * quantity : 579
         */

        private String pName;
        private String pCode;
        private String pUnit;
        private String pSpec;
        private int quantity;

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public String getPCode() {
            return pCode;
        }

        public void setPCode(String pCode) {
            this.pCode = pCode;
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

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
