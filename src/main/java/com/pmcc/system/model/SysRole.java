package com.pmcc.system.model;

import com.pmcc.core.common.model.CommonEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @ClassName: SysRole <br>
 * @Description: TODO角色表
 * @Date: 2020/12/26 10:13 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Entity
@Table(name = "sys_role")
public class SysRole extends CommonEntity {

    private String name;            //角色名称
    private String eName;           //英文名
    private String type;            //
    private int status;             //用户状态：1正常、0冻结、2删除(可以直接删除)
    private int sort;               //排序
    private String operator;        //操作者
    private Timestamp operateTime;  //操作时间
    private String operateIp;       //操作IP
    private String memo;            //备注

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "e_name")
    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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
    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
