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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
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
    public List<ExamBatch> getBatch(){
        return examBatchDao.getbatch();
    }
}
