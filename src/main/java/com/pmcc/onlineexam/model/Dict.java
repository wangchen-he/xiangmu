package com.pmcc.onlineexam.model;


import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


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


    private String dictname;        //工种全称

    private String dictid;          //工种id

    private String dicttype;        //工种的年份

    private String parentid;        //工种上级文件夹id

    private int rank;            //工等级

    private int sortno;          //工种排名

//    private String py;

    private int status;          //当前使用状态  1正常、0冻结、2删除

    @Column(name = "dictname")
    public String getDictName() {
        return this.dictname;
    }

    public void setDictName(String dictname) {
        this.dictname = dictname;
    }


    @Column(name = "dictid")
    public String getDictId() {
        return this.dictid;
    }

    public void setDictId(String dictid) {
        this.dictid = dictid;
    }

    @Column(name = "dicttype")
    public String getDictType() {
        return this.dicttype;
    }

    public void setDictType(String dicttype) {
        this.dicttype = dicttype;
    }

    @Column(name = "parentid")
    public String getParentId() {
        return this.parentid;
    }

    public void setParentId(String parentid) {
        this.parentid = parentid;
    }


    @Column(name = "rank")
    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    @Column(name = "sortno")
    public int getSortNo() {
        return this.sortno;
    }

    public void setSortNo(int sortno) {
        this.sortno = sortno;
    }


    @Column(name = "status")
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
