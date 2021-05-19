package com.pmcc.onlineexam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmcc.core.common.model.CommonEntity;
import com.pmcc.system.model.SysDictionaries;
import com.pmcc.system.model.SysUser;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="exam_question_bank")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ExamQuestionBank extends CommonEntity {
    private int test_type;  //试题类型 0单选 1多选  2判断
    private String test_content;//试题内容
    private String optionsA;//A选项
    private  String optionsB;
    private String optionsC;
    private String optionsD;
    private String optionsE;
    private String optionsF;
    private String right_answer; //正确答案
    private String difficult;//难易程度
    private int status;

    private String dict_id;
    private SysDictionaries dict;

    private String created_by;
    private SysUser user;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8:00")
    private Date created_time;

    private String updated_by;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8:00")
    private Date updated_time;

    private String memo;

    private  int chstatus; //审核状态
    @OneToOne
    @JoinColumn(name="dict_id", unique = true, insertable = false, updatable = false)
    public SysDictionaries getDict() {
        return dict;
    }

    public void setDict(SysDictionaries dict) {
        this.dict = dict;
    }

    public int getChstatus() {
        return chstatus;
    }

    public void setChstatus(int chstatus) {
        this.chstatus = chstatus;
    }

    @OneToOne
    @JoinColumn(name="created_by", unique = true, insertable = false, updatable = false)
    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public int getTest_type() {
        return test_type;
    }

    public void setTest_type(int test_type) {
        this.test_type = test_type;
    }

    public String getTest_content() {
        return test_content;
    }

    public void setTest_content(String test_content) {
        this.test_content = test_content;
    }

    public String getOptionsA() {
        return optionsA;
    }

    public void setOptionsA(String optionsA) {
        this.optionsA = optionsA;
    }

    public String getOptionsB() {
        return optionsB;
    }

    public void setOptionsB(String optionsB) {
        this.optionsB = optionsB;
    }

    public String getOptionsC() {
        return optionsC;
    }

    public void setOptionsC(String optionsC) {
        this.optionsC = optionsC;
    }

    public String getOptionsD() {
        return optionsD;
    }

    public void setOptionsD(String optionsD) {
        this.optionsD = optionsD;
    }

    public String getOptionsE() {
        return optionsE;
    }

    public void setOptionsE(String optionsE) {
        this.optionsE = optionsE;
    }

    public String getOptionsF() {
        return optionsF;
    }

    public void setOptionsF(String optionsF) {
        this.optionsF = optionsF;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDict_id() {
        return dict_id;
    }

    public void setDict_id(String dict_id) {
        this.dict_id = dict_id;
    }
    @Column(name="created_by")
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "ExamQuestionBank{" +
                "test_type=" + test_type +
                ", test_content='" + test_content + '\'' +
                ", optionsA='" + optionsA + '\'' +
                ", optionsB='" + optionsB + '\'' +
                ", optionsC='" + optionsC + '\'' +
                ", optionsD='" + optionsD + '\'' +
                ", optionsE='" + optionsE + '\'' +
                ", optionsF='" + optionsF + '\'' +
                ", right_answer='" + right_answer + '\'' +
                ", difficult='" + difficult + '\'' +
                ", status=" + status +
                ", dict_id='" + dict_id + '\'' +

                ", created_by='" + created_by + '\'' +

                ", created_time=" + created_time +
                ", updated_by='" + updated_by + '\'' +
                ", updated_time=" + updated_time +
                ", memo='" + memo + '\'' +
                ", chstatus=" + chstatus +
                '}';
    }
}
