package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class BillDetailsEntity implements Serializable {

    /**
     * pageIndex : 1
     * pageSize : 0
     * totalCount : 1
     * totalPages : 1
     * items : [{"operateType":20,"quantity":1,"pName":"倚天剑","pUnit":"支","pSpec":"把"},
     * {"operateType":20,"quantity":1,"pName":"倚天剑","pUnit":"支","pSpec":"把"}]
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
         * operateType : 20
         * quantity : 1
         * pName : 倚天剑
         * pUnit : 支
         * pSpec : 把
         */

        private int operateType;
        private int quantity;
        private String pName;
        private String pUnit;
        private String pSpec;

        public int getOperateType() {
            return operateType;
        }

        public void setOperateType(int operateType) {
            this.operateType = operateType;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
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
