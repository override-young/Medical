package cn.allen.medical.entry;

import java.io.Serializable;

public class MeMenu implements Serializable {
    private String id;
    private String name;
    private int count;
    private int resId;

    public MeMenu() {
    }

    public MeMenu(String id, String name, int count, int resId) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.resId = resId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
