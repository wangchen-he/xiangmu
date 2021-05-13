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
 * @ClassName: SysMenuRelation <br>
 * @Description: TODO 菜单关系
 * @Date: 2020/2/17 15:39 <br>
 * @Author: darkbf <br>
 * @Version: 1.0 <br>
 */
@Entity
@Table(name = "sys_menu_relation")
public class SysMenuRelation extends CommonEntity {

    private SysMenuRelation menuParent;  //映射本身OID 找到父ID
    private SysMenu menuID;            //对应子级ID(子机构在该表具有唯一值)
    private int sort;                  //排序
    private int status;                //状态  1正常、0冻结、2删除
    private String memo;               //备注

    private Set<SysMenuRelation> childrens = new LinkedHashSet<SysMenuRelation>(0);

    //父机构
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotFound(action= NotFoundAction.IGNORE)  // 找不到数据不抛异常
    public SysMenuRelation getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(SysMenuRelation menuParent) {
        this.menuParent = menuParent;
    }

    /**
     * FetchType.LAZY   延迟加载 懒加载
     * FetchType.EAGER  马上加载 急加载
     * @return
     */
    @OneToOne(fetch = FetchType.EAGER) //延迟加载
    @JoinColumn(name = "menu_id", unique = true) //name指的是主表的外键,unique表示是否是唯一的
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public SysMenu getMenuID() {
        return menuID;
    }

    public void setMenuID(SysMenu menuID) {
        this.menuID = menuID;
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menuParent")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("sort")
    public Set<SysMenuRelation> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<SysMenuRelation> childrens) {
        this.childrens = childrens;
    }
}
