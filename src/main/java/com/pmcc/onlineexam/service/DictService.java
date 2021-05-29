package com.pmcc.onlineexam.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.onlineexam.dao.DictDao;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @ClassName: DictService
 * @Description: 定义工种管理的路由
 * @Author: zky
 * @Date: 2021/4/6 10:58
 */

@Service
@Transactional
public class DictService extends CommonServiceImpl<Dict, String> {

    @Autowired
    private  DictDao dictDao;

    @Override
    protected AbstractBaseDao<Dict, String> getEntityDao() {
        return dictDao;
    }


    public  List<Dict> getall(){
        return  dictDao.getall();

    }



    public  List<Dict> getdict(){
        return  dictDao.getdict();

    }
    public void deleteid(String id){
        dictDao.deleteid(id);
    }


    public List<Dict> getlike(String like){
      return   dictDao.getlike(like);
    }

    /**
     *
     * @Description ：获取当前页里的工种数据
     * @param pageDto
     * @author zky
     * @Date 2021/4/7 9:32
     */
    public PageDto<Dict>addDictInfo(PageDto pageDto){



        System.out.println("service--当前页码："+pageDto.getPage());
        return dictDao.getAllDict(pageDto);
    }

    /**
     *
     * @Description: 根据dicttype获取dictname列表
     * @param dictType
     * @author: lzx
     * @Date: 2021/4/14 9:48
     */
    public List<Dict> findByDictType(String dictType){
        return dictDao.findByDictType(dictType);
    }

}
