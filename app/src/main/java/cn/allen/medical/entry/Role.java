package cn.allen.medical.entry;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
    private String id;
    private String text;
    private String code;
    private int sort;
    private List<Role> childList;

    public Role() {
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

    public List<Role> getChildList() {
        return childList;
    }

    public void setChildList(List<Role> childList) {
        this.childList = childList;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", code='" + code + '\'' +
                ", sort=" + sort +
                ", childList=" + childList +
                '}';
    }
}
