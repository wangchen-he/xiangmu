package com.pmcc.onlineexam.controller;


import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.Dict;
import com.pmcc.onlineexam.service.DictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;


/**
 * Created with IntelliJ IDEA.
 *
 * @author： 刘志星
 * @date： 2021/4/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */

import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * @ClassName:  DictController
 * @Description ：TODO 对工种进行操作
 * @author zky
 * @Date 2021/4/6 11:31
 */

@RestController
@RequestMapping("/exam/dict")
public class DictController {

    @Resource
    private DictService dictService;

    /**
     *
     * @Description：对从数据表exam_dict中获取工种数据，并设置父子路由
     * @param pageDto
     * @author zky
     * @Date 2021/4/7 9:28
     */
    @GetMapping("/get-dictinfo")
    public PageDto<Dict> addDictInfo(PageDto pageDto) {
        return dictService.addDictInfo(pageDto);

    }

    @GetMapping("/get-dictinfo-all")
    public List<Dict> get(){
        List<Dict> list = dictService.findAll();
        return list;
    }

    @GetMapping("/findById")
    public Dict findById(String id){
        Dict dict = dictService.findById(id);
        return dict;

    }

    @GetMapping("/find-dictId")
    public  Dict findId(@RequestParam("oid") String oid){
        Dict dict = dictService.findById(oid);
        return dict;

    }

/**
 *
 * @Description ：新增工种信息
 * @param map
 * @author zky
 * @Date 2021/4/7 14:42
 */
    @PostMapping("/add-dictinfo")
    public ApiResult add(@RequestBody LinkedHashMap<String, String> map) {
        //判断map有数据
        if(map.size() == 0){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }

        String dictinfoDictname = " ";
        String dictinfoDicttype =" ";
        int Sortno = 0;

        dictinfoDictname = map.get("dictinfoDictname");
        dictinfoDicttype = map.get("dictinfoDicttype");
        Sortno = Integer.parseInt(map.get("Sortno"));
        Dict dict = new Dict();

        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        dict.setDictName(dictinfoDictname);
        dict.setDictType(dictinfoDicttype);
//        dict.setStatus(CommonConstant.STATUS_NORMAL);
        dict.setSortNo(Sortno);


        if(map.get("Sortno")!=null){
//            int Sortno = Integer.valueOf(map.get("Sortno"));
            dict.setSortNo(Sortno);  //
        }

//        dict.setOperator(jwtUserService.onLineUser().getId());
//        dict.setOperateTime(timenow);           //创建时间
        dictService.save(dict);
        return ApiResult.success();
    }

    @PostMapping("/add-dict")
    public Dict add(
//            @RequestParam("id") String id,
            @RequestParam("rank") Integer rank,
            @RequestParam("status") Integer status,
            @RequestParam("dictType")String dictType,
            @RequestParam("dictId")String dictId,
            @RequestParam("dictName")String dictName,
            @RequestParam("parentId")String parentId,
            @RequestParam("sortNo")Integer sortNo
    )
    {
        Dict dict = new Dict();
        dict.setDictId(dictId);
        dict.setRank(rank);
        dict.setStatus(status);
        dict.setDictType(dictType);
        dict.setDictName(dictName);
        dict.setParentId(parentId);
        dict.setSortNo(sortNo);

        return dictService.save(dict);
    }

    /**
     *
     * @Description: 根据dicttype获取dictname列表
     * @param dictType
     * @author: lzx
     * @Date: 2021/4/14 10:12
     */
    @GetMapping("/findByDictType")
    public List<Map<String,String>> findByDictType(String dictType){
        List<Dict> Dicts = dictService.findByDictType(dictType);
        List<Map<String,String>> DictMaps = new ArrayList<>();

        for (int i = 0; i < Dicts.size(); i++) {
            Dict dict = Dicts.get(i);
            Map<String, String> DictMap = new HashMap<>();
            DictMap.put("id",dict.getDictId());
            DictMap.put("dictName",dict.getDictName());
            DictMaps.add(DictMap);
        }
        return DictMaps;
    }

    /**
     *
     * @Description 通过oid实现删除工种信息
     * @author zky
     * @Date 2021/4/9 10:56
     */
    @GetMapping("/del-dict")
    public ApiResult deleteById(@RequestParam("id") String id){
        if (dictService.deleteByID(id)){
            return ApiResult.success("删除成功");
        }
        else {
            return ApiResult.fail("删除失败");
        }
    }

    /**
     * 删除用户  假删除
     *
     * @param dictId
     * @return
     */
//    @PostMapping("/delete-user")
//    public ApiResult deleteUser(@RequestBody String dictId) {
////        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
////        String user = jwtUserService.onLineUser().getUserCName();
////        SysUser sysUser = sysUserService.findById(dictId);
//        Dict dict = dictService.findById(dictId);
////        sysUser.setStatus(CommonConstant.DEL_FLAG); //删除标记
//        dict.setStatus(CommonConstant.DEL_FLAG); //删除标记
////        sysUser.setOperator(user);
////        sysUser.setOperateTime(timenow);
//        dictService.update(dict);
//        return ApiResult.success();
//    }


    /**
     *
     * @Description :通过oid实现更新工种信息
     * @param dict
     * @author zky
     * @Date 2021/4/9 16:43
     */
    @PutMapping("update-dict")
    public ApiResult updateById(Dict dict) {
        dictService.update(dict);
        return ApiResult.success();
    }

}
