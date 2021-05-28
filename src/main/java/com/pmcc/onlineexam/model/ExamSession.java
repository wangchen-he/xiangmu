/**
 * Created with IntelliJ IDEA.
 *
 * @date： 2021/5/17
 * @description：ExamSession的实体类
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
@Table(name = "exam_session")    //场次表
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ExamSession extends CommonEntity {
    private static final long serialVersionUID = 1L;  //版本兼容

    /**
     * 批次id
     */
private String batchId;
    /**
     * 场次名称
     */

    private String sessionName;
    /**
     * 字典（工种）id
     */

    private String dictId;
    /**
     * 规则id（集成通用规则）
     */

    private String paperId;
    /**
     * 考试开始时间
     */

    private Date beginTime;
    /**
     * 考试结束时间
     */

    private Date endTime;
    /**
     * 开考状态
     */

    private int startStatus;
    /**
     * 试卷生成状态
     */

    private int paperStatus;
    /**
     * 数据状态
     */

    private int delFlag;
    /**
     * 考试类型
     */
    private String examType;
    /**
     * 创建人
     */

    private String createdBy;
    /**
     * 创建时间
     */

    private Date createdTime;
    /**
     * 更新人
     */

    private String updateBy;
    /**
     * 更新时间
     */

    private Date updateTime;
    /**
     * 备注
     */

    private String memo;


    @Column(name = "batch_id")
    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public static long getSerialversionuid() {
        return 1L;
    }


    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "session_name")
    public String getSessionName() {
        return this.sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    @Column(name = "dict_id")
    public String getDictId() {
        return this.dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    @Column(name = "paper_gene_rules_id")
    public String getPaperId() {
        return this.paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "time_begin")
    public Date getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "time_end")
    public Date getEndTime() {
        return this.endTime;
    }


    @Column(name = "start_status")
    public int getStartStatus() {
        return this.startStatus;
    }

    public void setStartStatus(int startStatus) {
        this.startStatus = startStatus;
    }

    @Column(name = "paper_gene_status")
    public int getPaperStatus() {
        return this.paperStatus;
    }

    public void setPaperStatus(int paperStatus) {
        this.paperStatus = paperStatus;
    }

    @Column(name = "del_flag")
    public int getDelFlag() {
        return this.delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Column(name = "exam_type")
    public String getExamType() {
        return this.examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }


}
//    @Column(name = "created_by")
//    public String getCreatedBy() {
//        return this.createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @Column(name = "created_time")
//    public Date getCreatedTime() {
//        return this.createdTime;
//    }
//
//    public void setCreatedTime(Date createdTime) {
//        this.createdTime = createdTime;
//    }
//
//    @Column(name = "updated_by")
//    public String getUpdateBy() {
//        return this.updateBy;
//    }
//
//    public void setUpdateBy(String updateBy) {
//        this.updateBy = updateBy;
//    }
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @Column(name = "updated_time")
//    public Date getUpdateTime() {
//        return this.updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
