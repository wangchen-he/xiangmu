package com.pmcc.onlineexam.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.onlineexam.dao.ExamPaperDao;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: ExamPaperService
 * @Description:
 * @Author: 94105
 * @Date: 2021/4/9 16:41
 */
@Service
public class ExamPaperService extends CommonServiceImpl<ExamPaper,String> {
    @Override
    protected AbstractBaseDao<ExamPaper, String> getEntityDao() {
        return examPaperDao;
    }

    @Autowired
    private ExamPaperDao examPaperDao;

    public PageDto<ExamPaper> query(PageDto pageDto){
        System.out.println("service--当前页码："+pageDto.getPage());
        return examPaperDao.getAllExamPaper(pageDto);

    }
}
