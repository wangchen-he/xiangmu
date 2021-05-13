package com.pmcc.onlineexam.model;


import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ClassName: ExamPaper
 * @Description:
 * @Author: 94105
 * @Date: 2021/4/9 11:01
 */
@Entity
@Table(name = "exam_paper")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ExamPaper extends CommonEntity {
    private String examBatch;

    private int judgeNo;

    private Double judgeScore;

    private int judgeEasyNo;

    private int judgeMidNo;

    private int judgeHardNo;

    private int singleNo;

    private Double singleScore;

    private int singleEasyNo;

    private int singleMidNo;

    private int singleHardNo;

    private int mulNo;

    private Double mulScore;

    private int mulEasyNo;

    private int mulMidNo;

    private int mulHardNo;

    private Double totalScore;

    public String getExamBatch() {
        return examBatch;
    }

    public void setExamBatch(String examBatch) {
        this.examBatch = examBatch;
    }

    public int getJudgeNo() {
        return judgeNo;
    }

    public void setJudgeNo(int judgeNo) {
        this.judgeNo = judgeNo;
    }

    public Double getJudgeScore() {
        return judgeScore;
    }

    public void setJudgeScore(Double judgeScore) {
        this.judgeScore = judgeScore;
    }

    public int getJudgeEasyNo() {
        return judgeEasyNo;
    }

    public void setJudgeEasyNo(int judgeEasyNo) {
        this.judgeEasyNo = judgeEasyNo;
    }

    public int getJudgeMidNo() {
        return judgeMidNo;
    }

    public void setJudgeMidNo(int judgeMidNo) {
        this.judgeMidNo = judgeMidNo;
    }

    public int getJudgeHardNo() {
        return judgeHardNo;
    }

    public void setJudgeHardNo(int judgeHardNo) {
        this.judgeHardNo = judgeHardNo;
    }

    public int getSingleNo() {
        return singleNo;
    }

    public void setSingleNo(int singleNo) {
        this.singleNo = singleNo;
    }

    public Double getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(Double singleScore) {
        this.singleScore = singleScore;
    }

    public int getSingleEasyNo() {
        return singleEasyNo;
    }

    public void setSingleEasyNo(int singleEasyNo) {
        this.singleEasyNo = singleEasyNo;
    }

    public int getSingleMidNo() {
        return singleMidNo;
    }

    public void setSingleMidNo(int singleMidNo) {
        this.singleMidNo = singleMidNo;
    }

    public int getSingleHardNo() {
        return singleHardNo;
    }

    public void setSingleHardNo(int singleHardNo) {
        this.singleHardNo = singleHardNo;
    }

    public int getMulNo() {
        return mulNo;
    }

    public void setMulNo(int mulNo) {
        this.mulNo = mulNo;
    }

    public Double getMulScore() {
        return mulScore;
    }

    public void setMulScore(Double mulScore) {
        this.mulScore = mulScore;
    }

    public int getMulEasyNo() {
        return mulEasyNo;
    }

    public void setMulEasyNo(int mulEasyNo) {
        this.mulEasyNo = mulEasyNo;
    }

    public int getMulMidNo() {
        return mulMidNo;
    }

    public void setMulMidNo(int mulMidNo) {
        this.mulMidNo = mulMidNo;
    }

    public int getMulHardNo() {
        return mulHardNo;
    }

    public void setMulHardNo(int mulHardNo) {
        this.mulHardNo = mulHardNo;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}
