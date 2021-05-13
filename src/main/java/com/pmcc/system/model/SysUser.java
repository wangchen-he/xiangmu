package com.pmcc.system.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.pmcc.core.common.model.CommonEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @ClassName: SysUser <br>
 * @Description: TODO(系统用户)
 * @Date: 2019/11/3 14:30 <br>
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
@Table(name = "sys_user")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysUser extends CommonEntity {

    private String userEName;            //英文名
    @JSONField(serialize = false)
    private String userCName;        //中文名
    private String password;            //密码
    private String code;            //编号
    private String icon;            //图标
    private int sort;            //排序
    private String mobile;            //电话
    private String email;            //邮箱
    private int status;            //用户状态：1正常、0冻结、2删除
    private String creator;            //创建者
    private Timestamp createTime;            //创建时间
    private String operator;            //操作者
    private Timestamp operateTime;            //操作时间
    private String operateIP;            //操作者IP
    private String memo;

    private int pwdChange;         //密码修改次数

    private String deptID;            //所属组织ID
    private String deptCName;            //所属组织
    private String deptEName;            //所属组织

    @Column(name = "user_ename")
    public String getUserEName() {
        return userEName;
    }

    public void setUserEName(String userEName) {
        this.userEName = userEName;
    }

    @Column(name = "user_cname")
    public String getUserCName() {
        return userCName;
    }

    public void setUserCName(String userCName) {
        this.userCName = userCName;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Column(name = "sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "operator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
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

    @Column(name = "pwd_change")
    public int getPwdChange() {
        return pwdChange;
    }

    public void setPwdChange(int pwdChange) {
        this.pwdChange = pwdChange;
    }

    @Column(name = "dept_id")
    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    @Column(name = "dept_cname")
    public String getDeptCName() {
        return deptCName;
    }

    public void setDeptCName(String deptCName) {
        this.deptCName = deptCName;
    }

    @Column(name = "dept_ename")
    public String getDeptEName() {
        return deptEName;
    }

    public void setDeptEName(String deptEName) {
        this.deptEName = deptEName;
    }
}
