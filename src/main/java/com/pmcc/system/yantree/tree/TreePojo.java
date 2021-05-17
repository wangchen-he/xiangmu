package com.pmcc.system.yantree.tree;

import com.pmcc.core.common.model.CommonEntity;
import com.pmcc.system.yantree.sysdep.DepPojo;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity //表明这个类是一个实体类，在数据库中存在对应的字段
@Table(name = "sys_dep_relation") //在数据库中对应的表
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TreePojo extends CommonEntity {


    private DepPojo dep;              //对应子级ID(子机构在该表具有唯一值)
    private String depId;
    private int status;                //用户状态：1正常、0冻结(冻结状态不可删除)、2删除  等同sysDep的status值
    @OneToOne
    @JoinColumn(name="dep_id", unique = true, insertable = false, updatable = false)
    public DepPojo getDep() {
        return dep;
    }

    public void setDep(DepPojo dep) {
        this.dep = dep;
    }
    @Column(name="dep_id")
    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }
    @Column(name="status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
