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
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamBatch;
import com.pmcc.onlineexam.model.ExamSession;
import com.pmcc.onlineexam.model.Person;
import com.pmcc.onlineexam.service.ExamBatchService;
import com.pmcc.onlineexam.service.ExamSessionService;
import com.pmcc.onlineexam.utils.GetUser;
import com.pmcc.system.common.CommonCode;
import com.pmcc.system.model.SysDepRelation;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/exam/exambatch")
public class ExamBatchController {


    @Autowired
    GetUser getUser;

    @Autowired
    ExamBatchService examBatchService;

    @Autowired
    ExamSessionService examSessionService;

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
        List<ExamBatch> batchList = examBatchService.GetBatch();
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
     * 条件查询
     *
     * @return
     */
    @GetMapping("/conditionQuery")
    public List<ExamBatch> conditionQuery(@RequestParam LinkedHashMap<String, String> linkedHashMap) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String name = "";
        String sessionType = "";
        Date testDatestare = null;
        Date testDateend = null;
        Date beginTimestare = null;
        Date beginTimeend = null;
        Date endTimestare = null;
        Date endTimeend = null;
        if (linkedHashMap.get("name") != null && !linkedHashMap.get("name").equals("null") && linkedHashMap.get("name").length() > 0) {
            name = linkedHashMap.get("name");
        }
        if (linkedHashMap.get("sessionType") != null && !linkedHashMap.get("sessionType").equals("null") && linkedHashMap.get("sessionType").length() > 0) {
            sessionType = linkedHashMap.get("sessionType");
        }
        try{
            if (linkedHashMap.get("testDatestare") != null && !linkedHashMap.get("testDatestare").equals("null") && linkedHashMap.get("testDatestare").length() > 0) {
                Date date = simpleDateFormat.parse(linkedHashMap.get("testDatestare"));
                testDatestare = new Timestamp(date.getTime());
            }
            if (linkedHashMap.get("testDateend") != null && !linkedHashMap.get("testDateend").equals("null") && linkedHashMap.get("testDateend").length() > 0) {
                Date date = simpleDateFormat.parse(linkedHashMap.get("testDateend"));
                testDateend = new Timestamp(date.getTime());
            }
            if (linkedHashMap.get("beginTimestare") != null && !linkedHashMap.get("beginTimestare").equals("null") && linkedHashMap.get("beginTimestare").length() > 0) {
                Date date = simpleDateFormat.parse(linkedHashMap.get("beginTimestare"));
                beginTimestare = new Timestamp(date.getTime());
            }
            if (linkedHashMap.get("beginTimeend") != null && !linkedHashMap.get("beginTimeend").equals("null") && linkedHashMap.get("beginTimeend").length() > 0) {
                Date date = simpleDateFormat.parse(linkedHashMap.get("beginTimeend"));
                beginTimeend = new Timestamp(date.getTime());
            }
            if (linkedHashMap.get("endTimestare") != null && !linkedHashMap.get("endTimestare").equals("null") && linkedHashMap.get("endTimestare").length() > 0) {
                Date date = simpleDateFormat.parse(linkedHashMap.get("endTimestare"));
                endTimestare = new Timestamp(date.getTime());
            }
            if (linkedHashMap.get("endTimeend") != null && !linkedHashMap.get("endTimeend").equals("null") && linkedHashMap.get("endTimeend").length() > 0) {
                Date date = simpleDateFormat.parse(linkedHashMap.get("endTimeend"));
                endTimeend = new Timestamp(date.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<ExamBatch> list = new ArrayList<>();
        list = examBatchService.conditionQuery(name, sessionType,testDatestare,testDateend,beginTimestare,beginTimeend,endTimestare,endTimeend);
//        system.out.println("");
        return list;
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    public ApiResult deleteById(@RequestBody String id) {
        if (id.length() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        /**
         * 批次数据的del_flag标记
         * */
//        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        SysUser ause=getUser.getUsername();
        ExamBatch examBatch = examBatchService.findById(id);
        examBatch.setDelFlag(CommonCode.DEL_FLAG);
//        examBatch.setUpdatedBy(ause.getId());
//        examBatch.setUpdatedTime(timenow);
        examBatchService.update(examBatch);

        List<ExamSession> examsessionIDs = examSessionService.findBySessionId(examBatch.getId());
        for (ExamSession examsessionID:examsessionIDs) {
            examsessionID.setDelFlag(CommonCode.DEL_FLAG); //删除标记
//            examsessionID.setUpdatedBy(ause.getId());
//            examsessionID.setUpdatedTime(timenow);
            examSessionService.update(examsessionID);
        }
        return ApiResult.success();
    }

    @PostMapping("/add")
    public ApiResult add(@RequestBody LinkedHashMap<String, String> map) {
        //判断map有数据
        if (map.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        Timestamp CreatedTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        ExamBatch exambatch = new ExamBatch();
        exambatch.setName(map.get("name"));
        exambatch.setYears(map.get("years"));
        exambatch.setWorkType(map.get("workType"));
        exambatch.setSessionType(map.get("sessionType"));
        exambatch.setOperateSite(map.get("operateSite"));
        exambatch.setContactPerson(map.get("contactPerson"));
        exambatch.setContact(map.get("contact"));
        exambatch.setEmail(map.get("email"));

        SysUser ause=getUser.getUsername();
        exambatch.setCreatedBy(ause.getId());

        exambatch.setCreatedTime(CreatedTime);
        exambatch.setMemo(map.get("memo"));
        exambatch.setDelFlag(CommonCode.STATUS_NORMAL);//是否被删除状态
        //exambatch.setPaperGenerate("0");//试卷是否配置状态
//        exambatch.setPaperGenerate(Integer.valueOf("0"));//试卷配置状态默认为0
//        exambatch.setSessionState(Integer.valueOf("0"));//
//        exambatch.setPaperConfigFlag(Integer.valueOf("0"));
//        exambatch.setTicketFlag("0");
        try {
            if (map.get("testDate")!=null){
                Date testDate = simpleDateFormat.parse(map.get("testDate"));
                exambatch.setTestDate(new Timestamp(testDate.getTime()));
            }
            if (map.get("beginTime")!=null){
                Date beginTime = simpleDateFormat.parse(map.get("beginTime"));
                exambatch.setBeginTime(new Timestamp(beginTime.getTime()));
            }
            if (map.get("endTime")!=null){
                Date endTime = simpleDateFormat.parse(map.get("endTime"));
                exambatch.setEndTime(new Timestamp(endTime.getTime()));
            }
            if (map.get("operateDate")!=null){
                Date operateDate = simpleDateFormat.parse(map.get("operateDate"));
                exambatch.setOperateDate(new Timestamp(operateDate.getTime()));
            }
        }catch (ParseException e) {
            e.printStackTrace();
        }
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
            exambatch.setName(map.get("name"));
            exambatch.setYears(map.get("years"));
            exambatch.setWorkType(map.get("workType"));
            exambatch.setSessionType(map.get("sessionType"));
            exambatch.setOperateSite(map.get("operateSite"));
            exambatch.setContactPerson(map.get("contactPerson"));
            exambatch.setContact(map.get("contact"));
            exambatch.setEmail(map.get("email"));
            SysUser ause=getUser.getUsername();
            exambatch.setUpdatedBy(ause.getId());
            Timestamp UpdatedTime = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            exambatch.setUpdatedTime(UpdatedTime);
            exambatch.setMemo(map.get("memo"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {
                if (map.get("testDate")!=null){
                    Date testDate = simpleDateFormat.parse(map.get("testDate"));
                    exambatch.setTestDate(new Timestamp(testDate.getTime()));
                }
                if (map.get("beginTime")!=null){
                    Date beginTime = simpleDateFormat.parse(map.get("beginTime"));
                    exambatch.setBeginTime(new Timestamp(beginTime.getTime()));
                }
                if (map.get("endTime")!=null){
                    Date endTime = simpleDateFormat.parse(map.get("endTime"));
                    exambatch.setEndTime(new Timestamp(endTime.getTime()));
                }
                if (map.get("operateDate")!=null){
                    Date operateDate = simpleDateFormat.parse(map.get("operateDate"));
                    exambatch.setOperateDate(new Timestamp(operateDate.getTime()));
                }
            }catch (ParseException e) {
                e.printStackTrace();
            }
            examBatchService.update(exambatch);
        }
        return ApiResult.success();
    }
}
