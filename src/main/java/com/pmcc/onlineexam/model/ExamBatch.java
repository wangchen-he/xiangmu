package com.pmcc.onlineexam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName: ExamBatch
 * @Description:TODO(ExamBatch实体类)
 * @Author: 94105
 * @Date: 2021/4/6 11:15
 */
@Entity
@Table(name = "exam_batch")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ExamBatch extends CommonEntity {
    private static final long serialVersionUID = 1L;

    private String name;

    private Date beginDate;

    private String contractPerson;

    private String contractMethod;

    private String remark;

    private String delFlag;

    private int isScoreView;

//    private String scoreView;

    private int sessionState;

    private int paperGenerate;

    private int paperConfigFlag;

    //private String paperview;

    private String ticketFlag;

    private String examAddress;

//    private String ticketFlagShow;

    @Column(name = "exam_address")
    public String getExamAddress() {
        return this.examAddress;
    }

    public void setExamAddress(String examAddress) {
        this.examAddress = examAddress;
    }

//    public String getTicketFlagShow() {
//        if ("0".equals(this.ticketFlag)) {
//            this.ticketFlagShow = "未生成";
//        } else if ("1".equals(this.ticketFlag)) {
//            this.ticketFlagShow = "已生成";
//        } else if ("2".equals(this.ticketFlag)) {
//            this.ticketFlagShow = "已清除";
//        } else {
//            this.ticketFlagShow = "未生成";
//        }
//        return this.ticketFlagShow;
//    }
//
//    public void setTicketFlagShow(String ticketFlagShow) {
//        this.ticketFlagShow = ticketFlagShow;
//    }

    @Column(name = "ticket_flag")
    public String getTicketFlag() {
        return this.ticketFlag;
    }

    public void setTicketFlag(String ticketFlag) {
        this.ticketFlag = ticketFlag;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "contract_person")
    public String getContractPerson() {
        return this.contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson;
    }

    @Column(name = "contract_method")
    public String getContractMethod() {
        return this.contractMethod;
    }

    public void setContractMethod(String contractMethod) {
        this.contractMethod = contractMethod;
    }

    @Column(name = "remark")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static long getSerialversionuid() {
        return 1L;
    }

    @Column(name = "del_flag")
    public String getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Column(name = "session_state")
    public int getSessionState() {
        return this.sessionState;
    }

    public void setSessionState(int sessionState) {
        this.sessionState = sessionState;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "begin_date")
    public Date getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Column(name = "paper_generate")
    public int getPaperGenerate() {
        return this.paperGenerate;
    }

    public void setPaperGenerate(int paperGenerate) {
        this.paperGenerate = paperGenerate;
    }

    @Column(name = "paper_config_flag")
    public int getPaperConfigFlag() {
        return this.paperConfigFlag;
    }

    public void setPaperConfigFlag(int paperConfigFlag) {
        this.paperConfigFlag = paperConfigFlag;
    }

//    public String getPaperview() {
//        if (this.paperConfigFlag==0) {
//            this.paperview = "未配置";
//        } else if (this.paperConfigFlag==1) {
//            this.paperview = "已配置";
//        } else if (this.paperConfigFlag==2) {
//            this.paperview = "显示";
//        } else {
//            this.paperview = "不显示";
//        }
//        return this.paperview;
//    }
//
//    public void setPaperview(String paperview) {
//        this.paperview = paperview;
//    }

    public int getIsScoreView() {
        return this.isScoreView;
    }

    public void setIsScoreView(int isScoreView) {
        this.isScoreView = isScoreView;
    }

//    public String getScoreView() {
//        return this.scoreView;
//    }
//
//    public void setScoreView(String scoreView) {
//        this.scoreView = scoreView;
//    }
}
