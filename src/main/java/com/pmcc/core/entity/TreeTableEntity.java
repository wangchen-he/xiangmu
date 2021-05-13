package com.pmcc.core.entity;

import java.util.List;

/**
 * @ClassName: TreeTableEntity <br>
 * @Description: TODO树形列表
 * @Date: 2020/5/20 15:20 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class TreeTableEntity {

    private String key;        //id
    private String text;      //显示内容
    private String link;      //链接
    private String type;      //类型
    private String icon;       //节点前的图标
    private int sort;       //排序
    private int status;       //状态
    private int disabled;       //是否显示
    private boolean isLeaf; // 是否为子节点
    private List<TreeTableEntity> children; //子节点集合

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public List<TreeTableEntity> getChildren() {
        return children;
    }

    public void setChildren(List<TreeTableEntity> children) {
        this.children = children;
    }
}
