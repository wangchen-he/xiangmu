package com.pmcc.system.model;

import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: SysMenu <br>
 * @Description: TODO 菜单
 * @Date: 2020/2/17 15:38 <br>
 * @Author: darkbf <br>
 * @Version: 1.0 <br>
 */
@Entity
@Table(name = "sys_menu")
public class SysMenu extends CommonEntity {

    private String text;            //模块名称
    private String link;            //链接
    private SysMenu parent;        //父ID
    private String type;            //类型，0 分组   1 模块   2 功能
    private String icon;            //图标
    private int status;             //状态  1正常、0冻结、2删除
    private int disabled;           //是否可用
    private int sort;               //排序
    private String i18n;            //国际化
    private String operator;        //操作者
    private Timestamp operateTime;     //操作时间
    private String operateIP;       //操作IP
    private String remarks;         //备注

    private List<SysMenu> childrens = new ArrayList<>();

    @Column(name = "TEXT")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "LINK")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    //父机构
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotFound(action= NotFoundAction.IGNORE)  // 找不到数据不抛异常
    public SysMenu getParent() {
        return parent;
    }

    public void setParent(SysMenu parent) {
        this.parent = parent;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "ICON")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "STATUS")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "DISABLED")
    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    @Column(name = "SORT")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Column(name = "I18N")
    public String getI18n() {
        return i18n;
    }

    public void setI18n(String i18n) {
        this.i18n = i18n;
    }

    @Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "OPERATE_TIME")
    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    @Column(name = "OPERATE_IP")
    public String getOperateIP() {
        return operateIP;
    }

    public void setOperateIP(String operateIP) {
        this.operateIP = operateIP;
    }

    @Column(name = "REMARKS")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    //子机构
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("sort")
    public List<SysMenu> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<SysMenu> childrens) {
        this.childrens = childrens;
    }
}
