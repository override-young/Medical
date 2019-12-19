package cn.allen.medical.entry;

import java.io.Serializable;

public class TodoCount implements Serializable {
    private int contractCount;//待处理合同数量
    private int priceCount;//	待确认价格数量
    private int billCount;//待处理账单数量
    private int totalCount;//总数量

    public TodoCount() {
    }

    public int getContractCount() {
        return contractCount;
    }

    public void setContractCount(int contractCount) {
        this.contractCount = contractCount;
    }

    public int getPriceCount() {
        return priceCount;
    }

    public void setPriceCount(int priceCount) {
        this.priceCount = priceCount;
    }

    public int getBillCount() {
        return billCount;
    }

    public void setBillCount(int billCount) {
        this.billCount = billCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
