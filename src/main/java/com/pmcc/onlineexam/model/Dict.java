package com.pmcc.onlineexam.model;


import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


/**
 * @ClassName: Dict
 * @Description: 定义dict表字段与实体类对应 ()
 * @Author: zky
 * @Date: 2021/4/6 9:45
 */

@Entity
@Table(name = "exam_dict")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)

public class Dict extends CommonEntity {

    private  String name;
    private String value;
    private String type;
    private int sort;
    private int status;
    private int del_flag;
    private String created_by;
    private Date created_time;

    private String updated_by;
    private Date updated_time;

    private  String memo; //备注
    private int date;//所属年份

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Date getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Date updated_time) {
        this.updated_time = updated_time;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
