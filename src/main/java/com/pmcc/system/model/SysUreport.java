package com.pmcc.system.model;

import com.pmcc.core.common.model.CommonEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @Classname SysUreport
 * @Description TODO
 * @Date 2021/1/5 16:39
 * @Created by yanglei
 * @Version 1.0
 */
@Entity
@Table(name = "sys_ureport")
public class SysUreport extends CommonEntity {


    private String ureportName;        //报表名 例如:报表文件名为:xxx.ureport.xml , 库表中存储信息为xxx
    private String fileName;        //文件名 例如:库表中存储信息为xxx.ureport.xml
    private String content;             //容量
    private SysUser creator;       //创建者
    private Timestamp createTime;      //创建时间
    private SysUser operator;            //操作者
    private Timestamp updateTime;            //操作时间
    private String operateIp;            //操作者IP
    private String memo;

    @Column(name = "ureport_name")
    public String getUreportName() {
        return ureportName;
    }

    public void setUreportName(String ureportName) {
        this.ureportName = ureportName;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "creator")
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

    @Column(name = "operator")
    public SysUser getOperator() {
        return operator;
    }

    public void setOperator(SysUser operator) {
        this.operator = operator;
    }

    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
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

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
