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

    private String years;

    private Date testDate;

    private String workType;

    private String sessionType;

    private Date beginTime;

    private Date endTime;

    private String contactPerson;

    private String contact;


    private String email;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private String memo;

//    @Column(name = "exam_address")
//    public String getExamAddress() {
//        return this.examAddress;
//    }
//
//    public void setExamAddress(String examAddress) {
//        this.examAddress = examAddress;
//    }

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

//    @Column(name = "ticket_flag")
//    public String getTicketFlag() {
//        return this.ticketFlag;
//    }
//
//    public void setTicketFlag(String ticketFlag) {
//        this.ticketFlag = ticketFlag;
//    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "years")
    public String getYears() {
        return this.years;
    }
    public void setYears(String years) {
        this.years = years;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "test_date")
    public Date getTestDate() {
        return this.testDate;
    }
    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    @Column(name = "work_type")
    public String getWorkType() {
        return this.workType;
    }
    public void setWorkType(String workType) {
        this.workType = workType;
    }

    @Column(name = "session_Type")
    public String getSessionType() {
        return this.sessionType;
    }
    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "begin_time")
    public Date getBeginTime() {
        return this.beginTime;
    }
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "end_time")
    public Date getEndTime() {
        return this.endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "contact_person")
    public String getContactPerson() {
        return this.contactPerson;
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Column(name = "contact")
    public String getContact() {
        return this.contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name = "email")
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "created_by")
    public String getCreatedBy() {
        return this.createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "created_time")
    public Date getCreatedTime() {
        return this.createdTime;
    }
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Column(name = "updated_by")
    public String getUpdatedBy() { return this.updatedBy; }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "updated_time")
    public Date getUpdatedTime() {
        return this.updatedTime;
    }
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Column(name = "memo")
    public String getMemo() {
        return this.memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }


//    public static long getSerialversionuid() {
//        return 1L;
//    }







//    @Column(name = "del_flag")
//    public String getDelFlag() {
//        return this.delFlag;
//    }
//
//    public void setDelFlag(String delFlag) {
//        this.delFlag = delFlag;
//    }
//
//    @Column(name = "session_state")
//    public int getSessionState() {
//        return this.sessionState;
//    }
//
//    public void setSessionState(int sessionState) {
//        this.sessionState = sessionState;
//    }
//
//
//    @Column(name = "paper_generate")
//    public int getPaperGenerate() {
//        return this.paperGenerate;
//    }
//
//    public void setPaperGenerate(int paperGenerate) {
//        this.paperGenerate = paperGenerate;
//    }
//
//    @Column(name = "paper_config_flag")
//    public int getPaperConfigFlag() {
//        return this.paperConfigFlag;
//    }
//
//    public void setPaperConfigFlag(int paperConfigFlag) {
//        this.paperConfigFlag = paperConfigFlag;
//    }

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
//
//    public int getIsScoreView() {
//        return this.isScoreView;
//    }
//
//    public void setIsScoreView(int isScoreView) {
//        this.isScoreView = isScoreView;
//    }
//
//    @Column(name = "session_type")
//    public String getSessionType() {
//        return this.sessionType;
//    }
//
//    public void setSessionType(String sessionType) {
//        this.sessionType = sessionType;
//    }

//    public String getScoreView() {
//        return this.scoreView;
//    }
//
//    public void setScoreView(String scoreView) {
//        this.scoreView = scoreView;
//    }
}
