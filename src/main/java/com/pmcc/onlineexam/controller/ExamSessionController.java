/**
 * Created with IntelliJ IDEA.
 * @date： 2021/5/17
 */

package com.pmcc.onlineexam.controller;

import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamSession;
import com.pmcc.onlineexam.service.ExamSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/exam/examsession")  //TODO 路由连接场次管理
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

    @GetMapping("/queryBySessionId")  //TODO 场次管理把批次id传过来
    public List<ExamSession> findBySessionId(String batchId){
        return examSessionService.findBySessionId(batchId);
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
        ExamSession examSession = examSessionService.findById(id); //TODO 调用通用方法
        if(examSession.getStartStatus()==0){
            examSession.setStartStatus(1);
        }else if(examSession.getStartStatus()==1){
            examSession.setStartStatus(2);
        }else if(examSession.getStartStatus()==2){
            examSession.setStartStatus(0);
        }

        examSessionService.update(examSession);
        return "success";
    }
}
