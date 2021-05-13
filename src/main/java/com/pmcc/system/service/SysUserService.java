package com.pmcc.system.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.system.dao.SysDepRelationDao;
import com.pmcc.system.dao.SysUserDao;
import com.pmcc.system.model.SysDep;
import com.pmcc.system.model.SysDepRelation;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: SysDepService <br>
 * @Description: 用户
 * @Date: 2019/11/13 15:38 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysUserService extends CommonServiceImpl<SysUser, String> {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysDepRelationDao sysDepRelationDao;

    @Override
    protected AbstractBaseDao<SysUser, String> getEntityDao() {
        // TODO Auto-generated method stub
        return sysUserDao;
    }

    public void addUser() {
        SysUser user = new SysUser();

    }

    public List<SysUser> getByDid(String deptID){
        return sysUserDao.getByDid(deptID);
    }

    /**
     * 根据机构ID获取用户列表
     *
     * @return
     */
    public List<SysUser> getUserByDep(String depID) {
        return sysUserDao.getUserByDep(depID);
    }

    /**
     * 根据用户英文名获取用户列表
     * @param eName
     * @return
     */
    public List<SysUser> getUserByEName(String eName) {
        return sysUserDao.getUserByEName(eName);
    }

    /************************** 用户-机构所有信息数据树形结构查询 ***************************/
    /**
     * 获取机构数Json串
     *
     * @return
     */
    @Transactional(readOnly = true)
    public String getUserTreeStr() {
        StringBuffer treeJson = new StringBuffer();
        String node = "-1";
        //加载根节点(根节点父ID为-1,没有对应值，用sql查询)
        List<SysDepRelation> rootList = sysDepRelationDao.getDepSQLByPRID(node);
        if (rootList.size() > 0) {
            treeJson.append(getUserNode(rootList, "")); //加载所有机构树
        }
        return treeJson.toString();
    }

    /**
     * 拼接结构树节点Json串
     *
     * @param reList
     * @return
     */
    public String getUserNode(List<SysDepRelation> reList,String json) {
        StringBuffer treeJson = new StringBuffer();
        String userJson = "";
        boolean firstNode = true;
        //自调用，判断传入的机构下级所属用户是否为空，
        // 若为空 则当前数据是初始数据 增加 “【”
        // 若不为空，曾当前机构下有用户，不是头，不增加“【”
        if(json.length() == 0){
            treeJson.append("[");
        }
        //循环机构关系列表
        for (SysDepRelation item : reList) {
            SysDep dep = item.getDepID();
            if (!firstNode) {
                treeJson.append(",");
            }
            treeJson.append("{");
            treeJson.append("title:'" + dep.getDepCName() + "'"); //机构名称
            treeJson.append(",key:'" + dep.getId() + "'"); //机构ID
            //展开第一层
            if (dep.getId().equals(CommonConstant.SYS_DEP)) {
                treeJson.append(",expanded: true"); //展开
            }
            treeJson.append(",icon:'" + dep.getIcon() + "'"); //机构图标
//            treeJson.append(",level:'" + dep.getLevel() + "'"); //层级
            //判断机构下属是否包含用户
            List<SysUser> userList = getUserByDep(dep.getId()); //查询该机构下用户
            if (userList.size() > 0) {
                userJson = getUserJson(userList);
            }
            //判断子集
            List<SysDepRelation> seList = sysDepRelationDao.getDepSQLByPRID(item.getId());
            if (seList.size() > 0) {
                //有子  无限向下查询 同时增加下属用户Json
                String childrenStr;
                if(userJson.length() > 0){
                    childrenStr = "[" + userJson + "," + getUserNode(seList,userJson) + "]";
                }else{
                    childrenStr = getUserNode(seList,userJson);
                }
                treeJson.append(",children:" + childrenStr);
            } else {
                //机构无子集  添加机构下属用户
                if (userJson.length() > 0) {
                    //构建用户Json
                    treeJson.append(",children: [" + userJson + "]");
                }else{
                    treeJson.append(",isLeaf: true");
                }
            }
            treeJson.append("}");
            if (firstNode) {
                firstNode = false;
            }
        }
        //自调用，判断传入的机构下级所属用户是否为空，
        // 若为空 则当前数据是初始数据 增加 “】”
        // 若不为空，曾当前机构下有用户，不是头，不增加“】”
        if(json.length() == 0){
            treeJson.append("]");
        }
        return treeJson.toString();
    }

    /**
     * @param userList
     * @return
     */
    public String getUserJson(List<SysUser> userList) {
        StringBuffer treeJson = new StringBuffer();
        boolean firstNode = true;
        //循环机构关系列表
        for (SysUser item : userList) {
            if (!firstNode) {
                treeJson.append(",");
            }
            treeJson.append("{");
            treeJson.append("title:'" + item.getUserCName() + "'"); //中文名
//            treeJson.append("userEName:'" + item.getUserEName() + "'"); //英文名
            treeJson.append(",key:'" + item.getId() + "'"); //用户ID
            treeJson.append(",icon: 'smile'"); //用户图标
            treeJson.append(",isLeaf: true");
            treeJson.append("}");
            if (firstNode) {
                firstNode = false;
            }
        }
        return treeJson.toString();
    }

}
