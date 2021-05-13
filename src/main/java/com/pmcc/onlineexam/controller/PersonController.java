/**
 * @ClassName: personController
 * @Description:
 * @Author: fangqian
 * @Date: 2021/4/6 10:58
 */

//@RestController
//@RequestMapping("/person/person")
package com.pmcc.onlineexam.controller;

import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;

import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.onlineexam.entity.PersonEntity;
import com.pmcc.onlineexam.model.Person;
import com.pmcc.onlineexam.service.PersonService;
//import com.pmcc.system.model.SysUser;
import com.pmcc.system.model.SysDep;
import com.pmcc.system.model.SysUser;
import com.pmcc.system.service.SysDepService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/exam/person")
public class PersonController {

    @Resource
    private PersonService personService;
    @Resource
    private JwtUserService jwtUserService;
    @Resource
    private SysDepService sysDepService;


    /**
     * 查询考生
     *
     * @param deptID
     * @return
     */
    @GetMapping("/get-person")
    public List<Person> getPerson(@RequestParam("dept") String deptID) {
        List<Person> list = new ArrayList<>();
        list = personService.getPersonByDep(deptID);
        return list;
    }


    /**
     * 条件查询
     *
     * @return
     */
    @GetMapping("/conditionQuery")
    public List<Person> conditionQuery(@RequestParam LinkedHashMap<String, String> linkedHashMap) {
        String personname = "";
        String sex = "";
        String idno = "";
        String race = "";
//        String auditstatus= "";
        String subject = "";
        if (linkedHashMap.get("personname") != null && !linkedHashMap.get("personname").equals("null") && linkedHashMap.get("personname").length() > 0) {
            personname = linkedHashMap.get("personname");
        }
        if (linkedHashMap.get("sex") != null && !linkedHashMap.get("sex").equals("null") && linkedHashMap.get("sex").length() > 0) {
            sex = linkedHashMap.get("sex");
        }
        if (linkedHashMap.get("idno") != null && !linkedHashMap.get("idno").equals("null") && linkedHashMap.get("idno").length() > 0) {
            idno = linkedHashMap.get("idno");
        }
        if (linkedHashMap.get("race") != null && !linkedHashMap.get("race").equals("null") && linkedHashMap.get("race").length() > 0) {
            race = linkedHashMap.get("race");
        }
//        if (linkedHashMap.get("auditstatus") != null && !linkedHashMap.get("auditstatus").equals("null") && linkedHashMap.get("auditstatus").length() > 0) {
//            race = linkedHashMap.get("auditstatus");
//        }
        if (linkedHashMap.get("subject") != null && !linkedHashMap.get("subject").equals("null") && linkedHashMap.get("subject").length() > 0) {
            subject = linkedHashMap.get("subject");
        }
        List<Person> list = new ArrayList<>();
        list = personService.conditionQuery(personname, sex, idno, race, subject);
//system.out.println("")
        return list;
    }

    @GetMapping("/detail")
    public PersonEntity detail(@RequestParam("oid") String oid){
        PersonEntity personEntity = new PersonEntity();
        Person person=new Person();
        person = personService.findById(oid);
//        Person person = result.get(0);
        if (person != null) {
            personEntity.setPerson(person);
        }
        return personEntity;
    }

//    @GetMapping("/getByID")
//    public Person getByID(@RequestParam("") String idno) {
//        Person person = new Person();
//        person = personService.findById(idno);
//        return person;
//    }


    @PostMapping("/add")
//    添加信息
    public ApiResult add(@RequestBody HashMap<String, String> map) {
        //判断map有数据
        if (map.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        SysDep sysDep;
        //获取机构列表，判断列表是否存在
        List<SysDep> depList = sysDepService.getDepByID(map.get("deptID"));
        if (depList.size() == 1) {
            sysDep = depList.get(0);
        } else {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, "机构信息不存在，请重新录入！");
        }
        SysUser user = jwtUserService.onLineUser();
        Person person = new Person();
        person.setPersonname(map.get("personname"));//姓名
        person.setSex(map.get("sex"));//性别
        person.setIdno(map.get("idno"));//证件号码
        person.setRace(map.get("race"));//民族
        person.setSubject(map.get("subject"));//工种
        person.setAuditStatus("0");//审核状态
        person.setCreatorBy(user);//创建人
        person.setCreatorTime(timenow);//创建时间
        person.setDeptID(sysDep.getId());
        person.setDeptCName(sysDep.getDepCName());
        person.setDeptEName(sysDep.getDepEName());

        personService.save(person);

//        return ApiResult.success(person);
        return ApiResult.success();
            }

    @PutMapping("/update")
    public ApiResult update(@RequestBody HashMap<String, String> map) {
        //判断map有数据
        if (map.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        SysDep sysDep;
        //获取机构列表，判断列表是否存在
        List<SysDep> depList = sysDepService.getDepByID(map.get("deptID"));
        if (depList.size() == 1) {
            sysDep = depList.get(0);
        } else {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, "机构信息不存在，请重新录入！");
        }
        SysUser user = jwtUserService.onLineUser();
        Person person  = personService.findById(map.get("id"));
        person.setPersonname(map.get("personname"));//姓名
        person.setSex(map.get("sex"));//性别
        person.setIdno(map.get("idno"));//证件号码
        person.setRace(map.get("race"));//民族
        person.setSubject(map.get("subject"));//工种
        person.setAuditStatus("0");//审核状态
        person.setUpdateBy(user);//更新人
        person.setCreatorTime(timenow);//创建时间
        person.setDeptID(sysDep.getId());
        person.setDeptCName(sysDep.getDepCName());
        person.setDeptEName(sysDep.getDepEName());
        personService.update(person);
        return ApiResult.success();
    }

    @PostMapping("/delete")
    public ApiResult del(@RequestBody String str) {

        if (str.length() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        String temp[] = str.split(",");
        List<Person> list = new ArrayList<>();

        //不验证类的关联性
        for (int i = 0; i < temp.length; i++) {
            String personID = temp[i];
            Person person = personService.findById(personID);
            personService.deleteByID(personID);
            list.add(person);
        }
        if (list.size() == temp.length) {
            return ApiResult.success();
        } else {
            return ApiResult.fail("删除失败");
        }
    }

    @PostMapping("/audit")
    public ApiResult audit(@RequestBody HashMap<String, String> map) {

        //判断map有数据
        if (map.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SysUser user = jwtUserService.onLineUser();

        Person person = personService.findById(map.get("id"));

        person.setReviewer(user);//审核人
        person.setAuditStatus(map.get("auditStatus"));//审核状态
        person.setAuditTime(timenow);//审核时间

        personService.update(person);
        return ApiResult.success();
    }

    @GetMapping("/getByAuditStatus")
    public List<Person> getByAuditStatus(@RequestParam("auditStatus")String auditStatus) {
        SysUser user = jwtUserService.onLineUser();
        List<Person> list = new ArrayList<>();
        if (user.getDeptID().equals(CommonConstant.SYS_DEP)) {
            list = personService.getByAuditStatus(auditStatus);
        } else {
            list = personService.getByAuditStatus(auditStatus, user.getDeptID());
        }

        return list;
    }

}





