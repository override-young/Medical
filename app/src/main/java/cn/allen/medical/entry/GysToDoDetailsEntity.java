package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class GysToDoDetailsEntity implements Serializable {

    /**
     * detailsList : [{"pName":"利群","pUnit":"单位","pSpec":"规格","quantity":1}]
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
    private List<DetailsListBean> detailsList;

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

    public List<DetailsListBean> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<DetailsListBean> detailsList) {
        this.detailsList = detailsList;
    }

    public static class DetailsListBean {
        /**
         * pName : 利群
         * pUnit : 单位
         * pSpec : 规格
         * quantity : 1
         */

        private String pName;
        private String pUnit;
        private String pSpec;
        private int quantity;

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

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
