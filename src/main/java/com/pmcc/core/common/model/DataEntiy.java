package com.pmcc.core.common.model;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * @ClassName: DataEntiy <br>
 * @Description: TODO（操作者数据Entity类）
 * @Date: 2019/11/3 13:52 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class DataEntiy extends CommonEntity {

    public String creator;  //创建者
    public Timestamp createTime; //创建时间
    public String operator;  //操作者
    public Timestamp operateTime;  //操作时间
    public String operateIp;  //操作者IP
    public String memo; //备注

    @Column(name = "User_EName")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Column(updatable=false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Timestamp getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Timestamp operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
