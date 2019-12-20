package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class ToDoBillEntity implements Serializable {

    /**
     * pageIndex : 1
     * pageSize : 0
     * totalCount : 1
     * totalPages : 1
     * items : [{"id":"d2d6e816-293a-455d-81ad-5ed2f1a72f6a","code":"FB19101200308",
     * "startTime":"2019-10-11 00:00:00","endTime":"2019-10-11 00:00:00"}]
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
         * id : d2d6e816-293a-455d-81ad-5ed2f1a72f6a
         * code : FB19101200308
         * startTime : 2019-10-11 00:00:00
         * endTime : 2019-10-11 00:00:00
         */

        private String id;
        private String code;
        private String startTime;
        private String endTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }

    @Override
    public String toString() {
        return "ToDoBillEntity{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                ", items=" + items +
                '}';
    }
}
