/**
 * Created with IntelliJ IDEA.
 *
 * @author： 刘志星
 * @date： 2021/4/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */

package com.pmcc.onlineexam.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.onlineexam.dao.ExamBatchDao;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.ExamBatch;
import com.pmcc.onlineexam.model.ExamSession;
import com.pmcc.onlineexam.model.Person;
import com.pmcc.system.model.SysDepRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class ExamBatchService extends CommonServiceImpl<ExamBatch,String> {

    @Autowired
    private ExamBatchDao examBatchDao;

    public PageDto<ExamBatch> query(PageDto pageDto){

//        System.out.println("service--当前页码："+pageDto.getPage());
        return examBatchDao.getAllExamBatch(pageDto);
    }


    @Override
    protected AbstractBaseDao<ExamBatch, String> getEntityDao() {
        return examBatchDao;
    }

    /**
     * 根据批次列表
     */
    public List<ExamBatch> GetBatch(){
        return examBatchDao.Getbatch();
    }
    /**
     * 查询
     */
    public List<ExamBatch> conditionQuery(String name, String sessionType, Date testDatestare,Date testDateend,Date beginTimestare,Date beginTimeend,Date endTimestare,Date endTimeend) {
        return examBatchDao.conditionQuery(name, sessionType,testDatestare,testDateend,beginTimestare,beginTimeend,endTimestare,endTimeend);
    }
}
