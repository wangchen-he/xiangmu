package com.pmcc.onlineexam.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.onlineexam.common.CommonCode;
import com.pmcc.onlineexam.dao.ExamQuestionBankDao;
import com.pmcc.onlineexam.model.ExamAudit;
import com.pmcc.onlineexam.model.ExamQuestionBank;
import com.pmcc.system.model.SysDictionaries;
import com.pmcc.system.service.SysDictionariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamQuestionBankService extends CommonServiceImpl<ExamQuestionBank, String> {
    @Autowired
    ExamQuestionBankDao examQuestionBankDao;
    @Autowired
    SysDictionariesService dictionariesService;

    @Override
    protected AbstractBaseDao<ExamQuestionBank, String> getEntityDao() {
        return examQuestionBankDao;
    }

    public List<ExamQuestionBank> getall() {
        return examQuestionBankDao.getall();
    }

    public void auto(String list, String tf) {
        if ("1".equals(tf)) {
            examQuestionBankDao.autopass(list);
        } else {
            examQuestionBankDao.autono(list);
        }
    }

    public void deletequ(String id) {
        examQuestionBankDao.deletequ(id);
    }

    public List<ExamQuestionBank> getlike(String data) {
        return examQuestionBankDao.getlike(data);
    }

    public List<SysDictionaries> getdict() {
        List<SysDictionaries> sysDictionariesByParentID = dictionariesService.getSysDictionariesByParentID(CommonCode.workid, CommonCode.STATUS_NORMAL.toString(), "0");
        return sysDictionariesByParentID;
    }



}
