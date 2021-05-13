package com.pmcc.system.model;

import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @ClassName: SysDepRelation <br>
 * @Description: TODO 组织机构上下级关系表
 * @Date: 2019/11/13 16:51 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Entity //表明这个类是一个实体类，在数据库中存在对应的字段
@Table(name = "sys_dep_relation") //在数据库中对应的表
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class SysDepRelation extends CommonEntity {

    private SysDepRelation depParent;  //映射本身OID 找到父ID
    private SysDep depID;              //对应子级ID(子机构在该表具有唯一值)
    private int level;                 //层级
    private int sort;                  //排序
    private int status;                //用户状态：1正常、0冻结(冻结状态不可删除)、2删除  等同sysDep的status值
    private String memo;

    private Set<SysDepRelation> childrens = new LinkedHashSet<SysDepRelation>(0);

    //父机构
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotFound(action= NotFoundAction.IGNORE)  // 找不到数据不抛异常
    public SysDepRelation getDepParent() {
        return depParent;
    }

    public void setDepParent(SysDepRelation depParent) {
        this.depParent = depParent;
    }

    /**
     * FetchType.LAZY   延迟加载 懒加载
     * FetchType.EAGER  马上加载 急加载
     * @return
     */
    @OneToOne(fetch = FetchType.EAGER) //延迟加载
    @JoinColumn(name = "dep_id", unique = true) //name指的是主表的外键,unique表示是否是唯一的
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public SysDep getDepID() {
        return depID;
    }

    public void setDepID(SysDep depID) {
        this.depID = depID;
    }

    @Column(name = "level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Column(name = "sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    //子机构
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "depParent")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("sort")
    public Set<SysDepRelation> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<SysDepRelation> childrens) {
        this.childrens = childrens;
    }

    @Override
    public String toString() {
        return "SysDepRelation{" +
                "depParent=" + depParent +

                ", level=" + level +
                ", sort=" + sort +
                ", status=" + status +
                ", memo='" + memo + '\'' +

                '}';
    }
}
