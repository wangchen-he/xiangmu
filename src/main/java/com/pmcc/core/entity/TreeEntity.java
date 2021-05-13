package com.pmcc.core.entity;

import com.pmcc.system.model.SysDep;

import java.util.List;

/**
 * @ClassName: TreeEntity <br>
 * @Description: TODO nz-tree树
 * @Date: 2020/2/16 18:32 <br>
 * @Author: darkbf <br>
 * @Version: 1.0 <br>
 */
public class TreeEntity {
    private String title;      //标题
    private String key;        //id
    private String icon;       //节点前的图标
    private int level;      //层级
    private boolean isLeaf; // 是否为子节点
    private List<TreeEntity> children; //子节点集合
    private SysDep treeNode; //节点详细内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public List<TreeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<TreeEntity> children) {
        this.children = children;
    }

    public SysDep getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(SysDep treeNode) {
        this.treeNode = treeNode;
    }
}
