/**
 * Created with IntelliJ IDEA.
 *
 * @author： 刘志星
 * @date： 2021/4/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */

package com.pmcc.onlineexam.controller;

import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamBatch;
import com.pmcc.onlineexam.service.ExamBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/exam/exambatch")
public class ExamBatchController {

    @Autowired
    ExamBatchService examBatchService;

    @GetMapping("/get-exam-batch")
    public PageDto<ExamBatch> getExamBatch(PageDto pageDto) {

        return examBatchService.query(pageDto);
    }

    /**
     * 获取相关信息
     *
     * @return
     * @RequestParam("ID") String ID
     */
    @GetMapping("/get-batch")
    public List<ExamBatch> getBatch() {
        //获取批次列表
        List<ExamBatch> batchList = examBatchService.findAll();
        //获取机构人员列表-标记相应用户
        return batchList;
    }

    /**
     * 根据id查找
     */
    @GetMapping("/getById")
    public ExamBatch getByOID(@RequestParam("oid") String oid) {
        ExamBatch exambatch = examBatchService.findById(oid);
        return exambatch;
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    public ApiResult deleteById(@RequestBody String id) {
        if (id.length() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        ExamBatch examBatch = examBatchService.findById(id);
        examBatchService.delete(examBatch);
        return ApiResult.success();
    }

    @PostMapping("/add")
    public ApiResult add(@RequestBody LinkedHashMap<String, String> map) {
        //判断map有数据
        if (map.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
//        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        Timestamp Enddata = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        Timestamp TestDate = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Timestamp CreatedTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        Timestamp UpdatedTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        ExamBatch exambatch = new ExamBatch();
        exambatch.setName(map.get("name"));
        exambatch.setYears(map.get("years"));
//        exambatch.setTestDate(TestDate);
        exambatch.setWorkType(map.get("workType"));
        exambatch.setSessionType(map.get("sessionType"));
//        exambatch.setContractPerson( map.get("contractPerson"));
//        exambatch.setContractMethod(map.get("contractMethod"));
//        exambatch.setBeginTime(timenow);
//        exambatch.setEndTime(Enddata);
        exambatch.setContactPerson(map.get("contactPerson"));
        exambatch.setContact(map.get("contact"));
        exambatch.setEmail(map.get("email"));
        exambatch.setCreatedBy(map.get("createdBy"));
        exambatch.setCreatedTime(CreatedTime);
        exambatch.setUpdatedBy(map.get("updatedBy"));
//        exambatch.setUpdatedTime(UpdatedTime);
        exambatch.setMemo(map.get("memo"));
//        exambatch.setIsScoreView(Integer.parseInt(map.get("isScoreView")));
//        exambatch.setDelFlag("0");//是否被删除状态
        //exambatch.setPaperGenerate("0");//试卷是否配置状态
//        exambatch.setPaperGenerate(Integer.valueOf("0"));//试卷配置状态默认为0
//        exambatch.setSessionState(Integer.valueOf("0"));//
//        exambatch.setPaperConfigFlag(Integer.valueOf("0"));
//        exambatch.setTicketFlag("0");
        try {
            if (map.get("beginTime")!=null){
                Date beginTime = simpleDateFormat.parse(map.get("beginTime"));
                exambatch.setBeginTime(new Timestamp(beginTime.getTime()));
            }
            if (map.get("endTime")!=null){
                Date endTime = simpleDateFormat.parse(map.get("endTime"));
                exambatch.setEndTime(new Timestamp(endTime.getTime()));
            }
            if (map.get("testDate")!=null){
                Date testDate = simpleDateFormat.parse(map.get("testDate"));
                exambatch.setTestDate(new Timestamp(testDate.getTime()));
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }




//        try {
//            if (map.get("beginTime") != null) {
//                Date beginTime = simpleDateFormat.parse(map.get("beginTime"));
//                exambatch.setBeginTime(new Timestamp(beginTime.getTime()));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (map.get("endTime") != null) {
//                Date endTime = simpleDateFormat.parse(map.get("endTime"));
//                exambatch.setEndTime(new Timestamp(endTime.getTime()));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (map.get("testDate") != null) {
//                Date testDate = simpleDateFormat.parse(map.get("testDate"));
//                exambatch.setTestDate(new Timestamp(testDate.getTime()));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        examBatchService.save(exambatch);
        return ApiResult.success();

    }

    /**
     * 修改数据
     */
    @PostMapping("/update")
    public ApiResult update(@RequestBody LinkedHashMap<String, String> map) {
        //判断map有数据
        if (map.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        ExamBatch exambatch = examBatchService.findById(map.get("id"));
        if (exambatch != null) {
//            Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//            Timestamp Enddata = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//            Timestamp TestDate = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            Timestamp UpdatedTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            exambatch.setName(map.get("name"));
            exambatch.setYears(map.get("years"));
//            exambatch.setTestDate(TestDate);
            exambatch.setWorkType(map.get("workType"));
            exambatch.setSessionType(map.get("sessionType"));
//            exambatch.setBeginTime(timenow);
//            exambatch.setEndTime(Enddata);
            exambatch.setContactPerson(map.get("contactPerson"));
            exambatch.setContact(map.get("contact"));
            exambatch.setEmail(map.get("email"));
            exambatch.setCreatedBy(map.get("createdBy"));
            exambatch.setUpdatedBy(map.get("updatedBy"));
            exambatch.setUpdatedTime(UpdatedTime);
            exambatch.setMemo(map.get("memo"));
//            try {
//                if (map.get("beginTime") != null) {
//                    Date beginTime = simpleDateFormat.parse(map.get("beginTime"));
//                    exambatch.setBeginTime(new Timestamp(beginTime.getTime()));
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (map.get("endTime") != null) {
//                    Date endTime = simpleDateFormat.parse(map.get("endTime"));
//                    exambatch.setEndTime(new Timestamp(endTime.getTime()));
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (map.get("testDate") != null) {
//                    Date testDate = simpleDateFormat.parse(map.get("testDate"));
//                    exambatch.setTestDate(new Timestamp(testDate.getTime()));
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }





            try {
                if (map.get("beginTime")!=null){
                    Date beginTime = simpleDateFormat.parse(map.get("beginTime"));
                    exambatch.setBeginTime(new Timestamp(beginTime.getTime()));
                }
                if (map.get("endTime")!=null){
                    Date endTime = simpleDateFormat.parse(map.get("endTime"));
                    exambatch.setEndTime(new Timestamp(endTime.getTime()));
                }
                if (map.get("testDate")!=null){
                    Date testDate = simpleDateFormat.parse(map.get("testDate"));
                    exambatch.setTestDate(new Timestamp(testDate.getTime()));
                }
            }catch (ParseException e) {
                e.printStackTrace();
            }
                examBatchService.update(exambatch);
            }
            return ApiResult.success();
        }
    }
