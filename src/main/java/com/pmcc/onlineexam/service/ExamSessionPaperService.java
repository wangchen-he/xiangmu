/**
 * Created with IntelliJ IDEA.
 *
 * @author： 刘志星
 * @date： 2021/4/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */

package com.pmcc.onlineexam.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.onlineexam.dao.ExamSessionPaperDao;
import com.pmcc.onlineexam.model.ExamBatch;
import com.pmcc.onlineexam.model.ExamSessionPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExamSessionPaperService extends CommonServiceImpl<ExamSessionPaper,String> {


    @Autowired
    ExamSessionPaperDao examSessionPaperDao;

    @Override
    protected AbstractBaseDao<ExamSessionPaper, String> getEntityDao() {
        return examSessionPaperDao;
    }

    public ExamSessionPaper findByExamSession(String examSession){
        return examSessionPaperDao.findByExamSession(examSession);
    }



}
