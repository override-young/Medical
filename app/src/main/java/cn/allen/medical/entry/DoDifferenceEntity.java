package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class DoDifferenceEntity implements Serializable {

    /**
     * pageIndex : 0
     * pageSize : 0
     * totalCount : 0
     * totalPages : 0
     * items : [{"sid":"7e4f2729-fa3b-412a-8088-9d7827370a87","sName":"开发供应商",
     * "spid":"ab6c882c-8489-4196-aec9-1a61a363a06f",
     * "pid":"9d771ec1-98b3-48a2-8fcd-d42adf4c77ce","packageName":"屠龙刀","code":"P000TLD",
     * "pinYin":"","spec":"把","unit":"把","manufacturerName":"上海医疗器械(集团)有限公司手术器械厂"}]
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
         * sid : 7e4f2729-fa3b-412a-8088-9d7827370a87
         * sName : 开发供应商
         * spid : ab6c882c-8489-4196-aec9-1a61a363a06f
         * pid : 9d771ec1-98b3-48a2-8fcd-d42adf4c77ce
         * packageName : 屠龙刀
         * code : P000TLD
         * pinYin :
         * spec : 把
         * unit : 把
         * manufacturerName : 上海医疗器械(集团)有限公司手术器械厂
         */

        private String sid;
        private String sName;
        private String spid;
        private String pid;
        private String packageName;
        private String code;
        private String pinYin;
        private String spec;
        private String unit;
        private String manufacturerName;
        private int count;
        private boolean isAdd;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isAdd() {
            return isAdd;
        }

        public void setAdd(boolean add) {
            isAdd = add;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getSName() {
            return sName;
        }

        public void setSName(String sName) {
            this.sName = sName;
        }

        public String getSpid() {
            return spid;
        }

        public void setSpid(String spid) {
            this.spid = spid;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPinYin() {
            return pinYin;
        }

        public void setPinYin(String pinYin) {
            this.pinYin = pinYin;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getManufacturerName() {
            return manufacturerName;
        }

        public void setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
        }
    }
}
