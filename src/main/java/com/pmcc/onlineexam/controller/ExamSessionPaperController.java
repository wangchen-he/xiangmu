/**
 * Created with IntelliJ IDEA.
 *
 * @author： 刘志星
 * @date： 2021/4/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */

package com.pmcc.onlineexam.controller;

import com.pmcc.onlineexam.model.ExamSessionPaper;
import com.pmcc.onlineexam.service.ExamSessionPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/exam/examsessionpaper")
public class ExamSessionPaperController {

    @Autowired
    ExamSessionPaperService examSessionPaperService;

    @GetMapping("/find")
    @ResponseBody
    public ExamSessionPaper find(String examSession){

        ExamSessionPaper examSessionPaper = examSessionPaperService.findByExamSession(examSession);
        return examSessionPaper;
    }


}
