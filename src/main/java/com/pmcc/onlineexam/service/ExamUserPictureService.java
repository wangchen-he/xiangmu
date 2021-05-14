package com.pmcc.onlineexam.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.onlineexam.dao.ExamUserPictureDao;
import com.pmcc.onlineexam.model.ExamAudit;
import com.pmcc.onlineexam.model.ExamUserPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamUserPictureService  extends CommonServiceImpl<ExamUserPicture,String> {
    @Autowired
    ExamUserPictureDao examUserPictureDao;

    @Override
    protected AbstractBaseDao<ExamUserPicture, String> getEntityDao() {
        return examUserPictureDao;
    }
}
