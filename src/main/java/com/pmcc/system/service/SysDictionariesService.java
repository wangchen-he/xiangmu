package com.pmcc.system.service;

import com.pmcc.core.api.ApiResult;
import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.entity.SysDictionariesTreeTableEntity;
import com.pmcc.system.dao.SysDictionariesDao;
import com.pmcc.system.model.SysDictionaries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SysDictionariesService <br>
 * @Description:
 * @Date: 2021/1/22 16:44 <br>
 * @Author: zpxx-sunbang <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysDictionariesService extends CommonServiceImpl<SysDictionaries, String> {

    @Autowired
    private SysDictionariesDao sysDictionariesDao;

    @Override
    protected AbstractBaseDao<SysDictionaries, String> getEntityDao() {
        return sysDictionariesDao;
    }

    /**
     * SQL获取各根节点
     *
     * @param parentID   父级ID
     * @param deleteFlag 是否删除
     * @return
     */
    public List<SysDictionaries> getDictionariesRootNode(String parentID, String deleteFlag) {
        return sysDictionariesDao.getDictionariesRootNode(parentID, deleteFlag);
    }

    /**
     * 根据表名、列名、父级ID、是否启用、是否删除获取节点，主要用于获取字典内指定根节点信息、根节点下的子集信息
     *
     * @param tableName
     * @param fieldName
     * @param status
     * @param deleteFlag
     * @return
     */
    public List<SysDictionaries> getDictionariesByTableField(String tableName, String fieldName, String parentID, String status, String deleteFlag) {
        List<SysDictionaries> sysDictionariesList = sysDictionariesDao.getDictionariesByTableField(tableName, fieldName, parentID, status, deleteFlag);
        return sysDictionariesList;
    }

    /**
     * 根据父级ID获取子集
     *
     * @param parentID 父级ID
     * @return
     */
    public List<SysDictionaries> getSysDictionariesByParentID(String parentID, String deleteFlag) {
        return sysDictionariesDao.getSysDictionariesByParentID(parentID, deleteFlag);
    }

    /**
     * 根据父级ID获取子集
     *
     * @param parentID   父级ID
     * @param status     是否启用
     * @param deleteFlag 是否删除
     * @return
     */
    public List<SysDictionaries> getSysDictionariesByParentID(String parentID, String status, String deleteFlag) {
        return sysDictionariesDao.getSysDictionariesByParentID(parentID, status, deleteFlag);
    }

    /**
     * 在相同父级下，子集内名称、值不能相同
     *
     * @param dictName  名称
     * @param dictValue 值
     * @return
     */
    public ApiResult checkRepeat(String id, String parentID, String dictName, String dictValue) {
        List<SysDictionaries> sysDictionarieslist = getSysDictionariesByParentID(parentID, "", "0"); //父机构下所有子集
        if (sysDictionarieslist.size() > 0) {
            for (SysDictionaries item : sysDictionarieslist) {
                //判断字典名称
                if (dictName.equals(item.getDictName()) && !id.equals(item.getId())) {
                    return ApiResult.fail(ErrorCode.DATA_WRITE_CNAME_CODE, ErrorCode.DATA_WRITE_CNAME_MSG + "同一字典内字典名称不允许重名！");
                }
                //判断字典值
                if (dictValue.equals(item.getDictValue()) && !id.equals(item.getId())) {
                    return ApiResult.fail(ErrorCode.DATA_WRITE_CNAME_CODE, ErrorCode.DATA_WRITE_CNAME_MSG + "同一字典内字典值不允许重名！");
                }
            }
        }
        return null;
    }


    @Transactional(readOnly = true)
    public List<SysDictionariesTreeTableEntity> getTree(String parentID) {
        List<SysDictionariesTreeTableEntity> list = new ArrayList<>();
        List<SysDictionaries> sysDictionariesList = new ArrayList<>();
        if (parentID.length() > 0 && !parentID.equals("-1")) {
            sysDictionariesList = sysDictionariesDao.getSysDictionariesByParentID(parentID, "", "0");
        } else {
            sysDictionariesList = sysDictionariesDao.getDictionariesRootNode("-1", "0");
        }
        //加载根节点(根节点父ID为-1,没有对应值，用sql查询)

        if (sysDictionariesList.size() > 0) {
            list.addAll(allTree(sysDictionariesList)); //加载所有机构树
        }
        return list;
    }

    /**
     * 根据菜单关系列表 循环判断加载子菜单
     *
     * @param sysDictionariesList
     * @return
     */
    private List<SysDictionariesTreeTableEntity> allTree(List<SysDictionaries> sysDictionariesList) {
        List<SysDictionariesTreeTableEntity> list = new ArrayList<>();
        //循环机构关系列表
        for (SysDictionaries item : sysDictionariesList) {
            SysDictionariesTreeTableEntity entity = new SysDictionariesTreeTableEntity();
            entity.setKey(item.getId());
            entity.setDictName(item.getDictName());             //名称
            entity.setDictValue(item.getDictValue());           //值
            entity.setDictType(item.getDictType());             //类型
            entity.setSort(item.getSort());                     //排序
            entity.setStatus(item.getStatus());                 //是否启用
            //判断子集 (因为有假删除，这里需要另外查询)
            List<SysDictionaries> seList = sysDictionariesDao.getSysDictionariesByParentID(item.getId(), "", "0");
            if (seList.size() > 0) {
                //有子  无限向下查询
                entity.setChildren(allTree(seList));
            } else {
                //无子
                entity.setLeaf(true);
            }
            list.add(entity);
        }
        return list;
    }


}
