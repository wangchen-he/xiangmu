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

import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamSession;
import com.pmcc.onlineexam.service.ExamSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 * @Description:
 * @param
 * @return:
 * @author: lzx
 * @Date: 2021/4/7 9:50
 */

@Controller
@RequestMapping("/exam/examsession")
@RestController
public class ExamSessionController {

    @Autowired
    ExamSessionService examSessionService;

//    @GetMapping("/query")
//    @ResponseBody
//    public PageDto<ExamSession> query(PageDto pageDto){
//        return examSessionService.query(pageDto);
//    }

    @GetMapping("/query")
    public List<ExamSession> query(){
        return examSessionService.findAll();
    }

    @GetMapping("/queryById")
    public ExamSession query(String id){
        return examSessionService.findById(id);
    }

    @GetMapping("/queryBySessionId")
    public List<ExamSession> findBySessionId(String sessionId){
        return examSessionService.findBySessionId(sessionId);
    }



    @PostMapping("/save")
    public String save(@RequestBody ExamSession examSession){
        examSessionService.save(examSession);
        return "success";
    }


    @GetMapping("/delete")
    public String delete(String id){

        examSessionService.deleteByID(id);
        return "success";
    }


    @PostMapping("/update")
    public String update(@RequestBody ExamSession examSession){
        examSessionService.update(examSession);
        return "success";
    }

    @GetMapping("/updateBegin")
    public String updateBegin(String id){
        ExamSession examSession = examSessionService.findById(id);
        if(examSession.getBeginFlag()==0){
            examSession.setBeginFlag(1);
        }else if(examSession.getBeginFlag()==1){
            examSession.setBeginFlag(2);
        }else if(examSession.getBeginFlag()==2){
            examSession.setBeginFlag(0);
        }

        examSessionService.update(examSession);
        return "success";
    }
}
