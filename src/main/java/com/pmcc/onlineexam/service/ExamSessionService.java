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
import com.pmcc.onlineexam.dao.ExamSessionDao;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamSessionService extends CommonServiceImpl<ExamSession, String> {

    @Autowired
    ExamSessionDao examSessionDao;

    @Override
    protected AbstractBaseDao<ExamSession, String> getEntityDao() {
        return examSessionDao;
    }

    public PageDto<ExamSession> query(PageDto<ExamSession> pageDto){
        return examSessionDao.findBySessionIdExamBatch(pageDto);
    }

    public List<ExamSession> findBySessionId(String sessionId){
        return examSessionDao.findBySessionId(sessionId);
    }

}
