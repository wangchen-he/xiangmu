package com.pmcc.onlineexam.controller;

import com.pmcc.onlineexam.common.CommonCode;
import com.pmcc.onlineexam.model.ExamQuestionBank;
import com.pmcc.onlineexam.service.ExamQuestionBankService;
import com.pmcc.onlineexam.utils.GetUser;
import com.pmcc.system.model.SysDictionaries;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/exam/question")
public class ExamQuestionBankController {
    @Autowired
    ExamQuestionBankService bankService;
    @Autowired
    GetUser user;
    @GetMapping("/getall")
    public List<ExamQuestionBank> getall(){
        return bankService.getall();
    }

/**
 * @author:
 * @description: 添加试卷
 * @date: 2021-05-18 15:19
 * @param “表单数据
 * @return
 */
    @PostMapping("/postdata")
    public  String postdata(@RequestBody ExamQuestionBank examQuestionBank){
        String userid=user.getUsername().getId();
        examQuestionBank.setCreated_by(userid);
        examQuestionBank.setCreated_time(new Date());
        examQuestionBank.setChstatus(CommonCode.UN_AUDITO);
        examQuestionBank.setStatus(CommonCode.STATUS_NORMAL);

        System.out.println(examQuestionBank);
        bankService.save(examQuestionBank);
        return "ok";
    }
/**
 * @author:
 * @description: 审核试题
 * @date: 2021-05-18 15:45
 * @param "id
 * @return
 */
    @GetMapping("auto")
    public  void auto(String list,String tf){
          String  lists=GetUser.getStringli(list);
          bankService.auto(lists,tf);

    }
    /**
     * @author:
     * @description: 删除用户
     * @date: 2021-05-18 16:19
     * @param "id
     * @return
     */
    @GetMapping("deletequ")
    public  void deletequ(String id){
        bankService.deletequ(id);
    }

    /**
     * @author: 条件查询
     * @description: TODO
     * @date: 2021-05-18 16:21
     * @param “null
     * @return
     */
    @GetMapping("getlike")
    public List<ExamQuestionBank> getlike(String data){
        List<ExamQuestionBank> list=bankService.getlike(data);
        return list;
    }

    @PostMapping("updetedata")
    public  void updetedata(@RequestBody LinkedHashMap<String, String> map){
        System.out.println(map);
       ExamQuestionBank upban= bankService.findById(map.get("id"));
       upban.setTest_content(map.get("test_content"));
       upban.setDifficult(map.get("difficult"));
        upban.setRight_answer(map.get("right_answer"));
        upban.setDict_id(map.get("dict_id"));
       upban.setUpdated_by(user.getUsername().getId());
       upban.setUpdated_time(new Date());
       if("0".equals(map.get("test_type"))){
           upban.setOptionsA(map.get("optionsA"));
           upban.setOptionsB(map.get("optionsB"));
           upban.setOptionsC(map.get("optionsC"));
           upban.setOptionsD(map.get("optionsD"));
           bankService.update(upban);
       }else if("1".equals(map.get("test_type"))){
           upban.setOptionsA(map.get("optionsA"));
           upban.setOptionsB(map.get("optionsB"));
           upban.setOptionsC(map.get("optionsC"));
           upban.setOptionsD(map.get("optionsD"));
           upban.setOptionsE(map.get("optionsE"));
           upban.setOptionsF(map.get("optionsF"));
           bankService.update(upban);
       }else  if("2".equals(map.get("test_type"))){

           bankService.update(upban);
       }

    }

    /**
     * @author:
     * @description: TODO
     * @date: 2021-05-21 19:28
     * @param “获取工种字典
     * @return
     */
    @GetMapping("getdict")
    public List<SysDictionaries> getdict(){
        return bankService.getdict();

    }
}
