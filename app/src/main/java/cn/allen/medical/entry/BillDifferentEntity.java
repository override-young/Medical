package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class BillDifferentEntity implements Serializable {

    /**
     * id : d2d6e816-293a-455d-81ad-5ed2f1a72f6a
     * code : FB19101200308
     * startTime : 2019-10-11 00:00:00
     * endTime : 2019-10-11 00:00:00
     * status : 5
     * diffRecords : [{"differenceType":2,"quantity":2,"pName":"饮血剑","pUnit":"把","pSpec":"把"},
     * {"differenceType":1,"quantity":1,"pName":"倚天剑","pUnit":"支","pSpec":"把"}]
     */

    private String id;
    private String code;
    private String startTime;
    private String endTime;
    private int status;
    private List<DiffRecordsBean> diffRecords;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DiffRecordsBean> getDiffRecords() {
        return diffRecords;
    }

    public void setDiffRecords(List<DiffRecordsBean> diffRecords) {
        this.diffRecords = diffRecords;
    }

    public static class DiffRecordsBean {
        /**
         * differenceType : 2
         * quantity : 2
         * pName : 饮血剑
         * pUnit : 把
         * pSpec : 把
         */

        private int differenceType;
        private int quantity;
        private String pName;
        private String pUnit;
        private String pSpec;

        public int getDifferenceType() {
            return differenceType;
        }

        public void setDifferenceType(int differenceType) {
            this.differenceType = differenceType;
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
