package com.pmcc.system.service;

import com.alibaba.fastjson.JSON;
import com.pmcc.core.api.ApiResult;
import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.entity.TreeEntity;
import com.pmcc.system.dao.SysDepDao;
import com.pmcc.system.dao.SysDepRelationDao;
import com.pmcc.system.model.SysDep;
import com.pmcc.system.model.SysDepRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SysDepService <br>
 * @Description: 组织机构
 * @Date: 2020/2/5 15:38 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysDepService extends CommonServiceImpl<SysDep, String> {

    @Autowired
    private SysDepDao sysDepDao;

    @Autowired
    private SysDepRelationDao sysDepRelationDao;

    @Override
    protected AbstractBaseDao<SysDep, String> getEntityDao() {
        // TODO Auto-generated method stub
        return sysDepDao;
    }

    /**
     * 同一父机构下 英文名、中文名、编号不重复
     *
     * @param depCname
     * @param depEname
     * @param code
     */
    public ApiResult checkRepeat(String parentID, String depCname, String depEname, String code) {
        List<SysDep> list = getchildrens(parentID); //父机构下所有子集
        if (list.size() > 0) {
            for (SysDep item : list) {
                //判断中文名
                if (depCname.equals(item.getDepCName())) {
                    return ApiResult.fail(ErrorCode.DATA_WRITE_CNAME_CODE, ErrorCode.DATA_WRITE_CNAME_MSG + "同一机构下不允许重名！");
                }
                //判断英文名
                if (depEname.equals(item.getDepEName())) {
                    return ApiResult.fail(ErrorCode.DATA_WRITE_ENAME_CODE, ErrorCode.DATA_WRITE_ENAME_MSG + "同一机构下不允许重名！");
                }
                //判断编号
                if (code.equals(item.getCode())) {
                    return ApiResult.fail(ErrorCode.DATA_WRITE_CODE_CODE, ErrorCode.DATA_WRITE_CODE_MSG + "同一机构下编码需要唯一！");
                }
            }
        }
        return null;
    }

    /**
     * 根据映射表父ID 获取子集
     *
     * @param parentID
     * @return
     */
    public List<SysDep> getchildrens(String parentID) {
        List<SysDep> depList = new ArrayList<>();
        List<SysDepRelation> reList = sysDepRelationDao.getChildrens(parentID);
        if (reList.size() > 0) {
            for (SysDepRelation item : reList) {
                SysDep sysDep = item.getDepID();
                depList.add(sysDep);
            }
        }
        return depList;
    }

    /**
     * 根据机构ID获取正常使用的机构信息
     * @param depID
     * @return
     */
    public List<SysDep> getDepByID(String depID) {
        return sysDepDao.getDepByID(depID);
    }

    /**************************机构树***************************************/
    /**
     * @param
     * @return
     * @author 分级加载树形组件
     * 此处还可以扩展，例如设定特定节点为根结点，只要添加一个参数，再①处判断该节点是否为空
     * 1、如果为空就不以自定义的节点为根节点
     * 2、如果不为空则自定义节点为根节点
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<TreeEntity> getTree(String prID) {
        List<TreeEntity> list = new ArrayList<>();
        String node = "-1";
        if (prID.length() > 0) {
            node = prID;
        }
        //加载根节点(根节点父ID为-1,没有对应值，用sql查询)
        List<SysDepRelation> rootList = sysDepRelationDao.getDepSQLByPRID(node);
        if (rootList.size() > 0) {
            list.addAll(allTree(rootList)); //加载所有机构树
        }
        return list;
    }

    /**
     * 根据机构关系列表 循环判断加载子机构
     *
     * @param reList
     * @return
     */
    private List<TreeEntity> allTree(List<SysDepRelation> reList) {
        List<TreeEntity> list = new ArrayList<>();
        //循环机构关系列表
        for (SysDepRelation item : reList) {
            SysDep dep = item.getDepID();
            TreeEntity entity = new TreeEntity();
            entity.setTitle(dep.getDepCName());  //机构名称
            entity.setKey(dep.getId());  //机构ID
            entity.setIcon(dep.getIcon());  //机构图标
            entity.setLevel(dep.getLevel());  //层级
            //判断子集
            List<SysDepRelation> seList = sysDepRelationDao.getDepSQLByPRID(item.getId());
            if (seList.size() > 0) {
                //有子  无限向下查询
                entity.setChildren(allTree(seList));
            } else {
                //无子
                entity.setLeaf(true);
            }
            entity.setTreeNode(dep); //节点详情
            list.add(entity);
        }
        return list;
    }

    /**
     * 获取机构数Json串
     * @param prID
     * @return
     */
    @Transactional(readOnly = true)
    public String getTreeStr(String prID) {
        StringBuffer treeJson = new StringBuffer();
        String node = "-1";
        if (prID.length() > 0) {
            node = prID;
        }
        //加载根节点(根节点父ID为-1,没有对应值，用sql查询)
        List<SysDepRelation> rootList = sysDepRelationDao.getDepSQLByPRID(node);
        if (rootList.size() > 0) {
            treeJson.append(getTreeNode(rootList)); //加载所有机构树
        }
        System.out.println(treeJson.toString());
        return treeJson.toString();
    }

    /**
     * 拼接结构树节点Json串
     * @param reList
     * @return
     */
    public String getTreeNode(List<SysDepRelation> reList){
        StringBuffer treeJson = new StringBuffer("[");
        boolean firstNode = true;
        //循环机构关系列表
        //reList是parent_id=-1的数组
        for (SysDepRelation item : reList) {
           // System.out.println("item:"+item);
            SysDep dep = item.getDepID();//获取对应的dep表中的单条数据
            if(!firstNode){
                treeJson.append(",");
            }

            //添加这一个数据
            treeJson.append("{");
            treeJson.append("title:'" +  dep.getDepCName()+ "'"); //机构名称
            treeJson.append(",key:'" + dep.getId()+ "'"); //机构ID
            treeJson.append(",icon:'" +  dep.getIcon()+ "'"); //机构图标
            treeJson.append(",level:'" +  dep.getLevel()+ "'"); //层级
            //第一层展开
            //是第一层的话添加
            if(dep.getId().equals(CommonConstant.SYS_DEP)){
                treeJson.append(",expanded:'" +  true+ "'");
            }
           //获取paren_id 中与本id相同的子集
            List<SysDepRelation> seList = sysDepRelationDao.getDepSQLByPRID(item.getId());
            //判断子集
            if (seList.size() > 0) {
                //有子  无限向下查询
                treeJson.append(",children:" +  getTreeNode(seList));
            } else {
                //无子
                treeJson.append(",isLeaf:'" + true+ "'");
            }
            treeJson.append(",treeNode:" + JSON.toJSONString(dep));//节点详情
            treeJson.append("}");
            if(firstNode) {
                firstNode = false;
            }
        }
        treeJson.append("]");
        return treeJson.toString();
    }
}
