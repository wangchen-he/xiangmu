package com.pmcc.system.model;

import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @ClassName: SysDictionaries <br>
 * @Description:
 * @Date: 2021/1/22 16:26 <br>
 * @Author: zpxx-sunbang <br>
 * @Version: 1.0 <br>
 */
@Entity
@Table(name = "sys_dictionaries")
public class SysDictionaries extends CommonEntity {

    private String dictName;            //名称
    private String dictValue;           //值
    private String dictType;            //类型
    private SysDictionaries parentID;   //父级ID
    private int sort;                   //排序
    private String status;              //是否启用
    private String tableName;           //表名
    private String fieldName;           //列名
    private SysUser creatorBy;           //创建人
    private Timestamp creatorTime;      //创建时间
    private String remarks;             //备注
    private String deleteFlag;         //是否删除

    @Column(name = "dict_name")
    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    @Column(name = "dict_value")
    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    @Column(name = "dict_type")
    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    //父级
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotFound(action = NotFoundAction.IGNORE)  // 找不到数据不抛异常
    public SysDictionaries getParentID() {
        return parentID;
    }

    public void setParentID(SysDictionaries parentID) {
        this.parentID = parentID;
    }

    @Column(name = "sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Column(name = "field_name")
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @OneToOne(fetch = FetchType.EAGER) //延迟加载
    @JoinColumn(name = "creator_by", unique = true) //name指的是主表的外键,unique表示是否是唯一的
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public SysUser getCreatorBy() {
        return creatorBy;
    }

    public void setCreatorBy(SysUser creatorBy) {
        this.creatorBy = creatorBy;
    }

    @Column(name = "creator_time")
    public Timestamp getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Timestamp creatorTime) {
        this.creatorTime = creatorTime;
    }

    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "delete_flag")
    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
