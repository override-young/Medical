package cn.allen.medical.entry;

import java.io.Serializable;

public class SelectSumChartEntity implements Serializable {

    /**
     * data1 : 12
     * data2 : 600
     */

    private int data1;
    private int data2;

    public SelectSumChartEntity() {
    }

    public SelectSumChartEntity(int data1, int data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public int getData1() {
        return data1;
    }

    public void setData1(int data1) {
        this.data1 = data1;
    }

    public int getData2() {
        return data2;
    }

    public void setData2(int data2) {
        this.data2 = data2;
    }
}
