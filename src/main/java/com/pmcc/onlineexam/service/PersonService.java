/**
 * @ClassName: PersonService
 * @Description:
 * @Author: fangqian
 * @Date: 2021/4/6 15:56
 */


package com.pmcc.onlineexam.service;


import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
//import com.pmcc.exam.dao.PersonDao;
//import com.pmcc.exam.model.Person;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.onlineexam.dao.PersonDao;
import com.pmcc.onlineexam.model.Person;
import com.pmcc.system.dao.SysDepRelationDao;
import com.pmcc.system.model.SysDep;
import com.pmcc.system.model.SysDepRelation;

import com.pmcc.system.model.SysUser;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class PersonService extends CommonServiceImpl<Person, String> {


    @Autowired
    private PersonDao personDao;

    @Autowired
    private SysDepRelationDao sysDepRelationDao;

    @Override
    protected AbstractBaseDao<Person, String> getEntityDao() {
        return personDao;
    }



//    根据身份证号获取

//    @Override
    public List<Person>findByIdno(String idno){
        return personDao.findByIdno(idno);
}
    /**
     * 根据机构ID获取用户列表
     *
     * @return
     */
//    public List<Person> getPersonByDep(String depID) {
//        return personDao.getPersonByDep(depID);
//    }
    public List<Person> getPersonByDep(String depID) {
        return personDao.getPersonByDep(depID);
    }
    /**
     * 按条件查询信息
     *
     *
     * @param personname 姓名
     * @param sex        性别

     * @param idno       证件号码
     * @param race       民族
     * @param subject    工种
     * @return
     */

//    @table("exam_student")
    public List<Person> conditionQuery(String personname, String sex,String idno, String race, String subject) {
        return personDao.conditionQuery(personname, sex,idno, race, subject);
    }

//    public void addUser() {
//        SysUser user = new SysUser();
//
//    }


    /**
     * 根据考生审核状态查询
     *
     * @param auditStatus
     * @return
     */
    public List<Person> getByAuditStatus(String auditStatus) {
        return personDao.getByAuditStatus(auditStatus);
    }
    /**
     * 根据考生审核状态查询
     *
     * @param auditStatus
     * @param depID
     * @return
     */
    public List<Person> getByAuditStatus(String depID,String auditStatus ) {
        return personDao.getByAuditStatus(depID,auditStatus );
    }

    /************************** 用户-机构所有信息数据树形结构查询 ***************************/
    /**
     * 获取机构数Json串
     *
     * @return
     */

    @Transactional(readOnly = true)
    public String getPersonTreeStr() {
        StringBuffer treeJson = new StringBuffer();
        String node = "-1";
        //加载根节点(根节点父ID为-1,没有对应值，用sql查询)
        List<SysDepRelation> rootList = sysDepRelationDao.getDepSQLByPRID(node);
        if (rootList.size() > 0) {
            treeJson.append(getPersonNode(rootList, "")); //加载所有机构树
        }
        return treeJson.toString();
    }
    /**
     * 拼接结构树节点Json串
     *
     * @param reList
     * @return
     */
    public String getPersonNode(List<SysDepRelation> reList,String json) {
        StringBuffer treeJson = new StringBuffer();
        String personJson="";
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
            List<Person>personList=getPersonByDep(dep.getId());//查询该机构下考生
            if (personList.size()>0){
                personJson=getPersonJson(personList);
            }
            //判断子集
            List<SysDepRelation> seList = sysDepRelationDao.getDepSQLByPRID(item.getId());
            if (seList.size() > 0) {
                //有子  无限向下查询 同时增加下属用户Json
                String childrenStr;
                if(personJson.length()>0){
                    childrenStr="["+personJson+","+getPersonNode(seList,personJson)+"]";
                }else{
                    childrenStr = getPersonNode(seList,personJson);
                }
                treeJson.append(",children:" + childrenStr);
            } else {
                //机构无子集  添加机构下属用户
                if (personJson.length()>0){
//                        构建考生Json

                    treeJson.append(",children: [" + personJson + "]");
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
     * @param personList
     * @return
     */
    public String getPersonJson(List<Person> personList) {

        StringBuffer treeJson = new StringBuffer();
        boolean firstNode = true;
        //循环机构关系列表
        for(Person item : personList){
            if (!firstNode) {
                treeJson.append(",");
            }
            treeJson.append("{");
            treeJson.append("title:'" + item.getPersonname()+"'");//姓名
//            treeJson.append(",key:'" + item.getId()+"'");//
            treeJson.append(",icon: 'smile'"); //用户图标
//            treeJson.append(",isLeaf: true");
            treeJson.append("}");
            if (firstNode) {
                firstNode = false;
            }
        }
        return treeJson.toString();
    }
    }

