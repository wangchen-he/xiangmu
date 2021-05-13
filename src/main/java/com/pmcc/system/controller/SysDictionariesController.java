package com.pmcc.system.controller;

import com.alibaba.fastjson.JSON;
import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.entity.SysDictionariesTreeTableEntity;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.system.model.SysDictionaries;
import com.pmcc.system.model.SysUser;
import com.pmcc.system.service.SysDictionariesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @ClassName: SysDictionariesController <br>
 * @Description:
 * @Date: 2021/1/25 8:52 <br>
 * @Author: zpxx-sunbang <br>
 * @Version: 1.0 <br>
 */
@RestController
@RequestMapping("/sys/dictionaries")
public class SysDictionariesController {

    @Resource
    private SysDictionariesService sysDictionariesService;

    @Resource
    private JwtUserService jwtUserService;

    /**
     * 根据表名、列名、父级ID、是否启用、是否删除获取节点，获取字典内指定根节点信息、根节点下的子集信息
     *
     * @return
     */
    @GetMapping("getTreeByTableField")
    public List<SysDictionaries> getTreeByTableField(@RequestParam LinkedHashMap<String, String> linkedHashMap) {
        String tableName = (String) linkedHashMap.get("tableName");
        String fieldName = (String) linkedHashMap.get("fieldName");
        String parentID = (String) linkedHashMap.get("parentID");
        String status = (String) linkedHashMap.get("status");
        String deleteFlag = (String) linkedHashMap.get("deleteFlag");

        //获取父级节点信息
        List<SysDictionaries> sysDictionariesList = sysDictionariesService.getDictionariesByTableField(tableName, fieldName, parentID, status, deleteFlag);

        if (sysDictionariesList.size() > 0) {
            parentID = sysDictionariesList.get(0).getId();
            List<SysDictionaries> result = sysDictionariesService.getSysDictionariesByParentID(parentID, "1", "0");
            return result;
        } else {
            return null;
        }

    }

    /**
     * SQL获取各根节点
     *
     * @return
     */
    @GetMapping("getDictionariesRootNode")
    public List<SysDictionaries> getDictionariesRootNode(@RequestParam LinkedHashMap<String, String> linkedHashMap) {
        String parentID = (String) linkedHashMap.get("parentID");
        String deleteFlag = (String) linkedHashMap.get("deleteFlag");

        //获取根节点信息
        List<SysDictionaries> sysDictionariesList = sysDictionariesService.getDictionariesRootNode(parentID, deleteFlag);

        if (sysDictionariesList.size() > 0) {
            return sysDictionariesList;
        } else {
            return null;
        }

    }

    /**
     * 获取机构树
     *
     * @return
     */
    @GetMapping("get")
    public List<SysDictionariesTreeTableEntity> getAllDictionaries(@RequestParam LinkedHashMap<String, String> linkedHashMap) {

        String parentID = "";

        List<SysDictionariesTreeTableEntity> list = new ArrayList<>();
        if (linkedHashMap.get("parentID") != null && !linkedHashMap.get("parentID").equals("null") && linkedHashMap.get("parentID").length() > 0) {
            parentID = linkedHashMap.get("parentID");
        }

        list = sysDictionariesService.getTree(parentID);
        return list;
    }

    /**
     * 根据ID获取指定字典值
     *
     * @param oid
     * @return
     */
    @GetMapping("getTreeByID")
    public SysDictionaries getDictionariesByID(@RequestParam("oid") String oid) {
        SysDictionaries sysDictionaries = new SysDictionaries();
        sysDictionaries = sysDictionariesService.findById(oid);
        return sysDictionaries;
    }

    /**
     * 新增
     *
     * @param linkedHashMap
     * @return
     */
    @PostMapping("/add")
    public ApiResult add(@RequestBody LinkedHashMap<String, String> linkedHashMap) {
        //判断map有数据
        if (linkedHashMap.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }

        String dictName = linkedHashMap.get("dictName");        //名称
        String dictValue = linkedHashMap.get("dictValue");      //值
        String dictType = linkedHashMap.get("dictType");        //类型
        String status = linkedHashMap.get("status").equals("true") ? "1" : "0";            //是否启用
        String remarks = linkedHashMap.get("remarks");          //备注
        int sort = 0;
        String parentID = linkedHashMap.get("parentID");        //父级ID

        if (linkedHashMap.get("sort") != null) {
            sort = Integer.valueOf(linkedHashMap.get("sort"));  //排序
        }

        //在相同父级下，子集内名称、值不能相同
        ApiResult repeat = sysDictionariesService.checkRepeat("", parentID, dictName, dictValue);
        if (repeat != null) {
            return repeat;
        }

        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        SysDictionaries sysDictionaries = new SysDictionaries();
        sysDictionaries.setDictName(dictName);                      //名称
        sysDictionaries.setDictValue(dictValue);                    //值
        sysDictionaries.setDictType(dictType);                      //类型
        sysDictionaries.setStatus(status);                          //是否启用
        SysDictionaries parentIDSysDictionaries = sysDictionariesService.findById(parentID);
        sysDictionaries.setParentID(parentIDSysDictionaries);
        sysDictionaries.setTableName(parentIDSysDictionaries.getTableName());                    //表名
        sysDictionaries.setFieldName(parentIDSysDictionaries.getFieldName());                    //列名
        sysDictionaries.setRemarks(remarks);                        //备注
        sysDictionaries.setSort(sort);                              //排序
        sysDictionaries.setDeleteFlag("0");
        sysDictionaries.setCreatorBy(jwtUserService.onLineUser());  //创建人
        sysDictionaries.setCreatorTime(timenow);                    //创建时间
        sysDictionariesService.save(sysDictionaries);

        return ApiResult.success();
    }

    /**
     * 修改
     *
     * @param linkedHashMap
     * @return
     */
    @PostMapping("update")
    public ApiResult update(@RequestBody LinkedHashMap<String, String> linkedHashMap) {
        //判断map有数据
        if (linkedHashMap.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }

        String id = linkedHashMap.get("id");
        String dictName = linkedHashMap.get("dictName");        //名称
        String dictValue = linkedHashMap.get("dictValue");      //值
        String dictType = linkedHashMap.get("dictType");        //类型
        String status = linkedHashMap.get("status").equals("true") ? "1" : "0";            //是否启用
        String remarks = linkedHashMap.get("remarks");          //备注
        int sort = 0;
        String parentID = linkedHashMap.get("parentID");        //父级ID

        if (linkedHashMap.get("sort") != null) {
            sort = Integer.valueOf(linkedHashMap.get("sort"));  //排序
        }

        //在相同父级下，子集内名称、值不能相同
        ApiResult repeat = sysDictionariesService.checkRepeat(id, parentID, dictName, dictValue);
        if (repeat != null) {
            return repeat;
        }

        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        SysDictionaries sysDictionaries = sysDictionariesService.findById(id);
        sysDictionaries.setDictName(dictName);                      //名称
        sysDictionaries.setDictValue(dictValue);                    //值
        sysDictionaries.setDictType(dictType);                      //类型
        sysDictionaries.setStatus(status);                          //是否启用

        SysDictionaries parentIDSysDictionaries = sysDictionariesService.findById(parentID);
        sysDictionaries.setParentID(parentIDSysDictionaries);
        sysDictionaries.setTableName(parentIDSysDictionaries.getTableName());                    //表名
        sysDictionaries.setFieldName(parentIDSysDictionaries.getFieldName());                    //列名

        sysDictionaries.setRemarks(remarks);                        //备注
        sysDictionaries.setSort(sort);                              //排序
        sysDictionaries.setCreatorBy(jwtUserService.onLineUser());  //创建人
        sysDictionaries.setCreatorTime(timenow);                    //创建时间
        sysDictionariesService.update(sysDictionaries);

        return ApiResult.success();
    }

    /**
     * 删除
     *
     * @param oid
     * @return
     */
    @PostMapping("delete")
    public ApiResult delete(@RequestBody String oid) {
        //不验证类的关联性
        SysDictionaries sysDictionaries = sysDictionariesService.findById(oid);
        //获取子集
        List<SysDictionaries> sysDictionariesChildList = sysDictionariesService.getSysDictionariesByParentID(oid, "0");

        boolean result = false;
        if (sysDictionariesChildList.size() > 0) {
            //递归删除子集
            result = delete(sysDictionariesChildList);
        } else {
            result = true;
        }

        if (result) {
            sysDictionaries.setDeleteFlag("1");
            sysDictionariesService.update(sysDictionaries);
            return ApiResult.success();
        } else {
            return ApiResult.fail("删除失败");
        }
    }

    public boolean delete(List<SysDictionaries> administrativeRegionList) {

        List<SysDictionaries> list = new ArrayList<>();

        for (SysDictionaries item : administrativeRegionList) {
            List<SysDictionaries> administrativeRegionChildList = sysDictionariesService.getSysDictionariesByParentID(item.getId(), "0");
            if (administrativeRegionChildList.size() > 0) {
                delete(administrativeRegionChildList);
            } else {
                list.add(item);
                item.setDeleteFlag("1");
                sysDictionariesService.update(item);
            }
        }
        if (list.size() == administrativeRegionList.size()) {
            return true;
        } else {
            return false;
        }
    }
}
