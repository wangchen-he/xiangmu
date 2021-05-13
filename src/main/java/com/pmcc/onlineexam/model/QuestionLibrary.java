package com.pmcc.onlineexam.model;


import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * @Description: 问题列表实体类
 * @return:
 * @author: lzx
 * @Date: 2021/4/20 9:47
 */

@Entity
@Table(name = "questions_library")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class QuestionLibrary extends CommonEntity {

    private String testType;

    private String testContent;

    private String ansA;

    private String ansB;

    private String ansC;

    private String ansD;

    private String ansE;

    private String ansF;

    private String rightAns;

    private String difficuty;

    private Date addTime;

    private String delFlag;

    private String typeOfWork;

    private String questionLevel;

    @Column(name = "test_type")
    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    @Column(name = "test_content")
    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    @Column(name = "ans_a")
    public String getAnsA() {
        return ansA;
    }

    public void setAnsA(String ansA) {
        this.ansA = ansA;
    }

    @Column(name = "ans_b")
    public String getAnsB() {
        return ansB;
    }

    public void setAnsB(String ansB) {
        this.ansB = ansB;
    }

    @Column(name = "ans_c")
    public String getAnsC() {
        return ansC;
    }

    public void setAnsC(String ansC) {
        this.ansC = ansC;
    }

    @Column(name = "ans_d")
    public String getAnsD() {
        return ansD;
    }

    public void setAnsD(String ansD) {
        this.ansD = ansD;
    }

    @Column(name = "ans_e")
    public String getAnsE() {
        return ansE;
    }

    public void setAnsE(String ansE) {
        this.ansE = ansE;
    }

    @Column(name = "ans_f")
    public String getAnsF() {
        return ansF;
    }

    public void setAnsF(String ansF) {
        this.ansF = ansF;
    }

    @Column(name = "right_ans")
    public String getRightAns() {
        return rightAns;
    }

    public void setRightAns(String rightAns) {
        this.rightAns = rightAns;
    }

    @Column(name = "difficuty")
    public String getDifficuty() {
        return difficuty;
    }

    public void setDifficuty(String difficuty) {
        this.difficuty = difficuty;
    }

    @Column(name = "addtime")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "del_flag")
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Column(name = "type_of_work")
    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    @Column(name = "question_level")
    public String getQuestionLevel() {
        return questionLevel;
    }

    public void setQuestionLevel(String questionLevel) {
        this.questionLevel = questionLevel;
    }
}

