package com.pmcc.onlineexam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmcc.core.common.model.CommonEntity;
import com.pmcc.system.model.SysDep;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "exam_user")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)

public class ExamAudit extends CommonEntity {
    private String  state; //审核状态
    private String name;
    private int age;
    private String id_card;
    //联系方式
    private String contact;



    private SysDep department;//所属部门
   private String depid;
    private String sex;
    private String remark;//备注
    private String image_id;//图片地址

    private String dict_id;  //职称

    private String created_by; //创建人
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date created_time; //


    private String updated_by;//更新人
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updated_time;//更新时间



    @Column(name = "department" )
    public String getDepid() {
        return depid;
    }
    public void setDepid(String depid) {
        this.depid = depid;
    }
    @OneToOne
    @JoinColumn(name="department", unique = true, insertable = false, updatable = false)
    public SysDep getDepartment() {
        return department;
    }
    public void setDepartment(SysDep department) {
        this.department = department;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getDict_id() {
        return dict_id;
    }

    public void setDict_id(String dict_id) {
        this.dict_id = dict_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Date getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Date updated_time) {
        this.updated_time = updated_time;
    }

    @Override
    public String toString() {
        return "ExamAudit{" +
                "state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", id_card='" + id_card + '\'' +
                ", contact='" + contact + '\'' +
                ", department=" + department +
                ", depid='" + depid + '\'' +
                ", sex='" + sex + '\'' +
                ", remark='" + remark + '\'' +
                ", image_id='" + image_id + '\'' +
                ", dict_id='" + dict_id + '\'' +
                ", created_by='" + created_by + '\'' +
                ", created_time=" + created_time +
                ", updated_by='" + updated_by + '\'' +
                ", updated_time=" + updated_time +
                '}';
    }
}
