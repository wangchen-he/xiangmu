package com.pmcc.core.entity;

import com.pmcc.system.model.SysDictionaries;

import java.util.List;

/**
 * @ClassName: SysDictionariesTreeEntity <br>
 * @Description:
 * @Date: 2021/1/22 17:47 <br>
 * @Author: zpxx-sunbang <br>
 * @Version: 1.0 <br>
 */
public class SysDictionariesTreeEntity {

    private String title;                           //标题
    private String key;                             //id
    private String icon;                            //节点前的图标
    private int level;                              //层级
    private boolean isLeaf;                         // 是否为子节点
    private List<SysDictionariesTreeEntity> children;   //子节点集合
    private SysDictionaries treeNode;               //节点详细内容

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

    public List<SysDictionariesTreeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<SysDictionariesTreeEntity> children) {
        this.children = children;
    }

    public SysDictionaries getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(SysDictionaries treeNode) {
        this.treeNode = treeNode;
    }
}
