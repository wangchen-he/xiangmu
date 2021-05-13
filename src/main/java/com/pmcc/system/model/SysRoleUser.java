package com.pmcc.system.model;

import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @ClassName: SysRoleUser <br>
 * @Description: TODO角色用户对照表
 * @Date: 2020/12/26 10:18 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Entity
@Table(name = "sys_role_user")
public class SysRoleUser extends CommonEntity {

    private SysRole role;              //映射到角色表
    private SysUser user;              //映射到用户
    private SysUser operator;          //操作者
    private Timestamp operateTime;     //操作时间
    private String operateIP;          //操作IP
    private String memo;               //备注

    @OneToOne(fetch = FetchType.EAGER) //延迟加载
    @JoinColumn(name = "role_id", unique = true) //name指的是主表的外键,unique表示是否是唯一的
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    @OneToOne(fetch = FetchType.EAGER) //延迟加载
    @JoinColumn(name = "user_id", unique = true) //name指的是主表的外键,unique表示是否是唯一的
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    @OneToOne(fetch = FetchType.EAGER) //延迟加载
    @JoinColumn(name = "operator", unique = true) //name指的是主表的外键,unique表示是否是唯一的
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public SysUser getOperator() {
        return operator;
    }

    public void setOperator(SysUser operator) {
        this.operator = operator;
    }

    @Column(name = "operate_time")
    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    @Column(name = "operate_ip")
    public String getOperateIP() {
        return operateIP;
    }

    public void setOperateIP(String operateIP) {
        this.operateIP = operateIP;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
