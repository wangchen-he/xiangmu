package com.pmcc.onlineexam.model;

import com.pmcc.core.common.model.CommonEntity;

import java.util.Date;

/**
 * @ClassName: Exam_Ticket
 * @Description: 定义exam_ticket表字段与实体类对应  (考试人员，位置、时间等，生成准考证、座位贴使用)
 * @Author: zky
 * @Date: 2021/4/6 10:17
 */
public class ExamTicket extends CommonEntity {

    private String name;            //考生姓名

    private String sex;             //考生性别

    private String photoUrl;        //考生照片url

    private String unit;            //考生所属工会

    private String unitName;        //考生所属工会名字

    private String examSubject;     //考试工种

    private String userCard;        //考生身份证号

    private String ticketNo;        //准考证号

    private String examRoom;        //考生考场号

    private Integer seatNo;         //考生座位号

    private String examTime;        //考试时间

    private String examAddress;     //考场地址

    private String sessionId;       //

    private String batchId;         //

    private Date createTime;        //准考证创建时间

    private String creater;         //创建人

    private String delFlag;         //

    private String flag;            //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(String examSubject) {
        this.examSubject = examSubject;
    }

    public String getUserCard() {
        return userCard;
    }

    public void setUserCard(String userCard) {
        this.userCard = userCard;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getExamRoom() {
        return examRoom;
    }

    public void setExamRoom(String examRoom) {
        this.examRoom = examRoom;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamAddress() {
        return examAddress;
    }

    public void setExamAddress(String examAddress) {
        this.examAddress = examAddress;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
