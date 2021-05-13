package com.pmcc.system.model;

import com.pmcc.core.common.model.CommonEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @ClassName: SysAcl <br>
 * @Description: TODO权限基础表
 * @Date: 2020/12/26 10:17 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 *  权限值包括
 *  visible-浏览 add-增加 update-修改 delete-删除 import-导入
 *  export-导出 audit-审核 download-下载 refresh-刷新 move-移动
 *  copy-移动
 */
@Entity
@Table(name = "sys_acl")
public class SysAcl extends CommonEntity {

    private String aclCname;            //权限名称
    private String aclEname;            //权限值
    private int code;                   //编码
    private int typeColor;              //标志颜色 根据色彩分等级 预设6个级别
    private String aclModuleId;         //
    private String url;                 //
    private String type;                //菜单、按钮、其他
    private String sort;                //部门层级排序
    private String status;              //用户状态：1正常、0冻结、2删除
    private SysUser creator;            //创造者
    private Timestamp createTime;       //创造时间
    private SysUser operator;           //操作者
    private Timestamp operateTime;      //操作时间
    private String operateIP;           //操作IP
    private String memo;                //备注

    @Column(name = "acl_cname")
    public String getAclCname() {
        return aclCname;
    }

    public void setAclCname(String aclCname) {
        this.aclCname = aclCname;
    }

    @Column(name = "acl_ename")
    public String getAclEname() {
        return aclEname;
    }

    public void setAclEname(String aclEname) {
        this.aclEname = aclEname;
    }

    @Column(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Column(name = "type_color")
    public int getTypeColor() {
        return typeColor;
    }

    public void setTypeColor(int typeColor) {
        this.typeColor = typeColor;
    }

    @Column(name = "acl_module_id")
    public String getAclModuleId() {
        return aclModuleId;
    }

    public void setAclModuleId(String aclModuleId) {
        this.aclModuleId = aclModuleId;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "sort")
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
