package cn.allen.medical.entry;

import java.io.Serializable;

public class TodoCount implements Serializable {
    private int contractCount;//待处理合同数量
    private int priceCount;//	待确认价格数量
    private int billCount;//待处理账单数量 设备科
    private int departmentBillCount;//待处理账单数量 科室
    private int totalCount;//总数量
    private int orderCount;//待确认采购订单数量

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

    public int getDepartmentBillCount() {
        return departmentBillCount;
    }

    public void setDepartmentBillCount(int departmentBillCount) {
        this.departmentBillCount = departmentBillCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    @Override
    public String toString() {
        return "TodoCount{" +
                "contractCount=" + contractCount +
                ", priceCount=" + priceCount +
                ", billCount=" + billCount +
                ", departmentBillCount=" + departmentBillCount +
                ", totalCount=" + totalCount +
                ", orderCount=" + orderCount +
                '}';
    }
}
