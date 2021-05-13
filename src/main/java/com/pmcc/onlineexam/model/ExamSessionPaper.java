/**
 * Created with IntelliJ IDEA.
 *
 * @author： 刘志星
 * @date： 2021/4/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */

package com.pmcc.onlineexam.model;

import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
//场次配制类
@Entity
@Table(name = "exam_session_paper")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ExamSessionPaper extends CommonEntity {

    private String examSession;

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

    @Column(name = "exam_session")
    public String getExamSession() {
        return this.examSession;
    }

    public void setExamSession(String examSession) {
        this.examSession = examSession;
    }

    @Column(name = "exam_batch")
    public String getExamBatch() {
        return examBatch;
    }

    public void setExamBatch(String examBatch) {
        this.examBatch = examBatch;
    }

    @Column(name = "judge_no")
    public int getJudgeNo() {
        return this.judgeNo;
    }

    public void setJudgeNo(int judgeNo) {
        this.judgeNo = judgeNo;
    }

    @Column(name = "judge_score")
    public Double getJudgeScore() {
        return this.judgeScore;
    }

    public void setJudgeScore(Double judgeScore) {
        this.judgeScore = judgeScore;
    }

    @Column(name = "judge_easy_no")
    public int getJudgeEasyNo() {
        return this.judgeEasyNo;
    }

    public void setJudgeEasyNo(int judgeEasyNo) {
        this.judgeEasyNo = judgeEasyNo;
    }

    @Column(name = "judge_mid_no")
    public int getJudgeMidNo() {
        return this.judgeMidNo;
    }

    public void setJudgeMidNo(int judgeMidNo) {
        this.judgeMidNo = judgeMidNo;
    }

    @Column(name = "judge_hard_no")
    public int getJudgeHardNo() {
        return this.judgeHardNo;
    }

    public void setJudgeHardNo(int judgeHardNo) {
        this.judgeHardNo = judgeHardNo;
    }

    @Column(name = "single_no")
    public int getSingleNo() {
        return this.singleNo;
    }

    public void setSingleNo(int singleNo) {
        this.singleNo = singleNo;
    }

    @Column(name = "single_score")
    public Double getSingleScore() {
        return this.singleScore;
    }

    public void setSingleScore(Double singleScore) {
        this.singleScore = singleScore;
    }

    @Column(name = "single_easy_no")
    public int getSingleEasyNo() {
        return this.singleEasyNo;
    }

    public void setSingleEasyNo(int singleEasyNo) {
        this.singleEasyNo = singleEasyNo;
    }

    @Column(name = "single_mid_no")
    public int getSingleMidNo() {
        return this.singleMidNo;
    }

    public void setSingleMidNo(int singleMidNo) {
        this.singleMidNo = singleMidNo;
    }

    @Column(name = "single_hard_no")
    public int getSingleHardNo() {
        return this.singleHardNo;
    }

    public void setSingleHardNo(int singleHardNo) {
        this.singleHardNo = singleHardNo;
    }

    @Column(name = "mul_no")
    public int getMulNo() {
        return this.mulNo;
    }

    public void setMulNo(int mulNo) {
        this.mulNo = mulNo;
    }

    @Column(name = "mul_score")
    public Double getMulScore() {
        return this.mulScore;
    }

    public void setMulScore(Double mulScore) {
        this.mulScore = mulScore;
    }

    @Column(name = "mul_easy_no")
    public int getMulEasyNo() {
        return this.mulEasyNo;
    }

    public void setMulEasyNo(int mulEasyNo) {
        this.mulEasyNo = mulEasyNo;
    }

    @Column(name = "mul_mid_no")
    public int getMulMidNo() {
        return this.mulMidNo;
    }

    public void setMulMidNo(int mulMidNo) {
        this.mulMidNo = mulMidNo;
    }

    @Column(name = "mul_hard_no")
    public int getMulHardNo() {
        return this.mulHardNo;
    }

    public void setMulHardNo(int mulHardNo) {
        this.mulHardNo = mulHardNo;
    }

    @Column(name = "total_score")
    public Double getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

}
