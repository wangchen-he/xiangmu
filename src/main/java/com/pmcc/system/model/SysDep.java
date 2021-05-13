package com.pmcc.system.model;

import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @ClassName: SysDep <br>
 * @Description: TODO(组织机构)
 * @Date: 2020/2/5 14:30 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 *
 *  * READ_ONLY 只读模式，在此模式下，如果对数据进行更新操作，会有异常；
 *  * READ_WRITE 读写模式在更新缓存的时候会把缓存里面的数据换成一个锁，
 *    其它事务如果去取相应的缓存数据，发现被锁了，直接就去数据库查询；
 *  * NONSTRICT_READ_WRITE 不严格的读写模式则不会的缓存数据加锁；
 *  DynamicUpdate: 只更新对象有改变的字段,默认是false;
 *  DynamicInsert: 只是插入那些不为空的字段默认是false;
 *
 */
@Entity
@Table(name = "sys_dep")
public class SysDep extends CommonEntity {

    private String depEName;            //英文名
    private String depCName;        //中文名
    private String code;            //编号
    private String icon;            //图标
    private String parentID;     //父节点ID
    private String parentName;   //父节点名称
    private int level;           //层级
    private int sort;            //排序
    private SysUser creator;            //创建者
    private Timestamp createTime;            //创建时间
    private SysUser operator;            //操作者
    private Timestamp operateTime;            //操作时间
    private String operate_ip;            //操作者IP
    private int status;            //用户状态：1正常、0冻结(冻结状态不可删除)、2删除
    private String memo;

    @Column(name = "dep_ename")
    public String getDepEName() {
        return depEName;
    }

    public void setDepEName(String depEName) {
        this.depEName = depEName;
    }

    @Column(name = "dep_cname")
    public String getDepCName() {
        return depCName;
    }

    public void setDepCName(String depCName) {
        this.depCName = depCName;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "parent_id")
    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    @Column(name = "parent_name")
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

    @OneToOne(fetch = FetchType.EAGER) //延迟加载
    @JoinColumn(name = "creator", unique = true) //name指的是主表的外键,unique表示是否是唯一的
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public SysUser getCreator() {
        return creator;
    }

    public void setCreator(SysUser creator) {
        this.creator = creator;
    }

    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
    public String getOperate_ip() {
        return operate_ip;
    }

    public void setOperate_ip(String operate_ip) {
        this.operate_ip = operate_ip;
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

    @Override
    public String toString() {
        return "SysDep{" +
                "depEName='" + depEName + '\'' +
                ", depCName='" + depCName + '\'' +
                ", code='" + code + '\'' +
                ", icon='" + icon + '\'' +
                ", parentID='" + parentID + '\'' +
                ", parentName='" + parentName + '\'' +
                ", level=" + level +
                ", sort=" + sort +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", operator=" + operator +
                ", operateTime=" + operateTime +
                ", operate_ip='" + operate_ip + '\'' +
                ", status=" + status +
                ", memo='" + memo + '\'' +
                '}';
    }
}
