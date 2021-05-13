/**
 * Created with IntelliJ IDEA.
 *
 * @author： 刘志星
 * @date： 2021/4/6
 * @description：ExamSession的实体类
 * @modifiedBy：
 * @version: 1.0
 */

package com.pmcc.onlineexam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmcc.core.common.model.CommonEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

//场次列表实体类
@Entity
@Table(name = "exam_session")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ExamSession extends CommonEntity {
    private static final long serialVersionUID = 1L;

    private String sessionId;

    private String examType;

    private Date beginTime;

    private Date endTime;

    private String remark;

    private String delFlag;

    private String typeOfWork;

    private int beginFlag;

    private int paperGenerate;

    private String examRoom;

    private int sortNo;


    @Column(name = "exam_room")
    public String getExamRoom() {
        return this.examRoom;
    }

    public void setExamRoom(String examRoom) {
        this.examRoom = examRoom;
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

    @Column(name = "session_id")
    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Column(name = "exam_type")
    public String getExamType() {
        return this.examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
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

    @Column(name = "type_of_work")
    public String getTypeOfWork() {
        return this.typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    @Column(name = "begin_flag")
    public int getBeginFlag() {
        return this.beginFlag;
    }

    public void setBeginFlag(int beginFlag) {
        this.beginFlag = beginFlag;
    }

    @Column(name = "paper_generate")
    public int getPaperGenerate() {
        return this.paperGenerate;
    }

    public void setPaperGenerate(int paperGenerate) {
        this.paperGenerate = paperGenerate;
    }

    @Column(name = "sort_no")
    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }
}


