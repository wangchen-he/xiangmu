package com.pmcc.onlineexam.controller;

import com.pmcc.core.api.ApiResult;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamBatch;
import com.pmcc.onlineexam.model.ExamPaper;
import com.pmcc.onlineexam.service.ExamBatchService;
import com.pmcc.onlineexam.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ExamPaperController
 * @Description:
 * @Author: 94105
 * @Date: 2021/4/9 16:51
 */

@Controller
@RequestMapping("examPaper")
public class ExamPaperController{
    @Autowired
    ExamPaperService examPaperService;
    @Autowired
    ExamBatchService examBatchService;

    /**
     * 查找所有
     * @param pageDto
     * @return
     */

    @GetMapping("/query")
    @ResponseBody
    public PageDto<ExamPaper> query(PageDto pageDto) {

        return examPaperService.query(pageDto);

    }

    /**
     * 通过exambatch中的oid查找
     * @param exambatch
     * @return
     */
    @GetMapping("/find")
    @ResponseBody
    public ExamPaper getByExamBatch(@RequestParam("exambatch") String exambatch){
        ExamPaper examPaper = examPaperService.findById(exambatch);
        return examPaper;
    }

    /**
     * 配置试题类型，对exambatch表部分列赋值
     * @param examPaper
     * @return
     */

    @PostMapping("/save")
    @ResponseBody
    public ApiResult save(ExamPaper examPaper){
        String id =examPaper.getId();
        if(id != null && !id.toString().equals("")){
            ExamBatch examBatch = new ExamBatch();
            examBatch.setId(examPaper.getExamBatch());
            examBatch=this.examBatchService.findById(examBatch.getId());
            if (examPaper.getTotalScore().doubleValue()>0.0D){
                examBatch.setPaperConfigFlag(1);
            }else{
                examBatch.setPaperConfigFlag(0);
            }
            this.examBatchService.save(examBatch);
            this.examPaperService.update(examPaper);
        }
        return ApiResult.success();
    }

}
