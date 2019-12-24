package cn.allen.medical.entry;

import java.io.Serializable;

public class DifferencesEntity implements Serializable {

    /**
     * spid : string
     * quantity : 0
     * remarks : string
     */

    private String spid;
    private int quantity;
    private String remarks;

    public String getSpid() {
        return spid;
    }

    public void setSpid(String spid) {
        this.spid = spid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
