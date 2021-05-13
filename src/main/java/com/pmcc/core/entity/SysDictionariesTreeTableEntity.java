package com.pmcc.core.entity;

import java.util.List;

/**
 * @ClassName: SysDictionariesTreeTableEntity <br>
 * @Description:
 * @Date: 2021/2/9 15:36 <br>
 * @Author: zpxx-sunbang <br>
 * @Version: 1.0 <br>
 */
public class SysDictionariesTreeTableEntity {

    private String key;         //id
    private String dictName;    //名称
    private String dictValue;   //值
    private String dictType;    //类型
    private int sort;           //排序
    private String status;         //是否启用
    private boolean isLeaf;     // 是否为子节点
    private List<SysDictionariesTreeTableEntity> children;  //子节点集合

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public List<SysDictionariesTreeTableEntity> getChildren() {
        return children;
    }

    public void setChildren(List<SysDictionariesTreeTableEntity> children) {
        this.children = children;
    }
}
