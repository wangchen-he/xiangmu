package com.pmcc.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pmcc.core.api.ApiResult;
//import com.darkbf.system.service.SysDepService;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.entity.TreeEntity;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.system.model.SysDep;
import com.pmcc.system.model.SysDepRelation;
import com.pmcc.system.model.SysUser;
import com.pmcc.system.service.SysDepRelationService;
import com.pmcc.system.service.SysDepService;
import com.pmcc.system.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: SysUserController <br>
 * @Description: TODO 组织机构
 * @Date: 2019/12/15 11:57 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 *     请求方法参考 ：https://blog.csdn.net/weixin_38004638/article/details/99655322
 * @RequestBody 接收数据时，前端不能使用GET方式提交数据，而是用POST方式进行提交
 * post 可以接收@RequestBody 与@RequestParam()
 * 同一个接收方法里，@RequestBody 与@RequestParam()可以同时使用，@RequestBody 最多只能有一个，而@RequestParam()可以有多个
 *
 */
@RestController
@RequestMapping("/sys/dep")
public class SysDepController {

    @Resource
    private SysDepService sysDepService;
    @Resource
    private SysDepRelationService sysDepRelationService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private JwtUserService jwtUserService;

    /**
     * 新增
     * @param map
     * @return
     */
    @PostMapping("/add")
    public ApiResult add(@RequestBody LinkedHashMap<String, String> map) {
        //判断map有数据
        if(map.size() == 0){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }

        String parentID,parentName,depCname,depEname,code,icon,sort,demo;
        parentID = map.get("parentID");
        depCname = map.get("depCName");
        depEname = map.get("depEName");
        code = map.get("code");
        icon = map.get("icon");
        sort = map.get("sort");
        demo = map.get("demo");

        //判断同一父机构下 英文名、中文名、编号不重复
        ApiResult repeat = sysDepService.checkRepeat(parentID, depCname, depEname, code);
        if(repeat != null){
            return repeat;
        }

        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        String name = jwtUserService.onLineUser().getUserCName();
        SysDep newDep = new SysDep();
        newDep.setDepCName(depCname);
        newDep.setDepEName(depEname);
        newDep.setCode(code);
        newDep.setIcon(icon);
        newDep.setCreateTime(timenow);
        newDep.setStatus(CommonConstant.STATUS_NORMAL);
        sysDepService.save(newDep);

        //映射表中父机构是否存在
        SysDepRelation depRelation = sysDepRelationService.getByDepID(parentID);
        SysDepRelation relation = new SysDepRelation();
        relation.setDepID(newDep);  //映射机构
        relation.setDepParent(depRelation); //父机构
        relation.setLevel(depRelation.getLevel()+1); //层级+1
        relation.setSort(Integer.parseInt(sort)); //排序
        sysDepRelationService.save(relation);

//        List<SysDep> list = new ArrayList<>();
//        list.add(newDep);
        return ApiResult.success();
    }

    /**
     * 修改
     * @param map
     * @return
     */
    @PutMapping("edit")
    public ApiResult update(@RequestBody LinkedHashMap<String, String> map){

        return ApiResult.success();
    }

    /**
     * 获取机构树
     * @return
     */
    @GetMapping("get")
    public Object getTree(@RequestParam LinkedHashMap<String, String> map){
        String prID = "";
        prID = (String) map.get("parent_id");
//        List<TreeEntity> list = new ArrayList<>();
//        list = sysDepService.getTree(prID);
//        return list;

        String treeStr = sysDepService.getTreeStr(prID);
        return JSON.parse(String.valueOf(treeStr));
    }

    /**
     * 删除
     * @param deptID
     * @return
     */
    @PostMapping("delete-dept")
    public ApiResult delete(@RequestBody String deptID){
       if(deptID.length() == 0){
           return ApiResult.fail(ErrorCode.DELETE_REFUSED_CODE, ErrorCode.DELETE_REFUSED_MSG);
       }
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        SysUser onlineUser = jwtUserService.onLineUser();
       SysDep sysDep = sysDepService.findById(deptID);
        //删除机构映射权限表
        SysDepRelation sysDepRelation = sysDepRelationService.getByDepID(deptID);
        //删除子机构及子机构用户
        List<SysDepRelation> relationList = sysDepRelationService.getChildrens(deptID);
        if(relationList.size()>0){
            for (SysDepRelation deptRelation:relationList) {
                //删除映射关系
                deptRelation.setStatus(CommonConstant.DEL_FLAG); //2 删除标记
                sysDepRelationService.update(deptRelation);
                //删除机构
                SysDep dept =deptRelation.getDepID();
                dept.setStatus(CommonConstant.DEL_FLAG); //2 删除标记
                dept.setOperator(onlineUser);
                dept.setOperateTime(timenow);
                sysDepService.update(dept);
                //删除机构下属用户
                List<SysUser> users = sysUserService.getUserByDep(dept.getId());
                for (SysUser user:users) {
                    user.setStatus(CommonConstant.DEL_FLAG); //删除标记
                    user.setOperator(onlineUser.getUserCName());
                    user.setOperateTime(timenow);
                    sysUserService.update(user);
                    //权限信息未清除
                }
            }
        }
        //删除映射关系
        sysDepRelation.setStatus(CommonConstant.DEL_FLAG); //2 删除标记
        sysDepRelationService.update(sysDepRelation);
        //删除机构
        sysDep.setStatus(CommonConstant.DEL_FLAG); //2 删除标记
        sysDep.setOperator(onlineUser);
        sysDep.setOperateTime(timenow);
        sysDepService.update(sysDep);
        //删除机构下属用户
        List<SysUser> users = sysUserService.getUserByDep(sysDep.getId());
        for (SysUser user:users) {
            user.setStatus(CommonConstant.DEL_FLAG); //删除标记
            user.setOperator(onlineUser.getUserCName());
            user.setOperateTime(timenow);
            sysUserService.update(user);
            //权限信息未清除
        }
        return ApiResult.success();
    }
}
