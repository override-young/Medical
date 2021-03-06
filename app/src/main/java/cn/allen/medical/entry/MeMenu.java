package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class MeMenu implements Serializable {
    private String id;
    private String text;
    private String code;
    private int sort;
    private List<MeMenu> childList;
    private int count;
    private int resId;

    public MeMenu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<MeMenu> getChildList() {
        return childList;
    }

    public void setChildList(List<MeMenu> childList) {
        this.childList = childList;
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

    @Override
    public String toString() {
        return "MeMenu{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", code='" + code + '\'' +
                ", sort=" + sort +
                ", childList=" + childList +
                ", count=" + count +
                ", resId=" + resId +
                '}';
    }
}
