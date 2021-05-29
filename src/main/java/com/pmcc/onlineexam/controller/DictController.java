package com.pmcc.onlineexam.controller;


import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.onlineexam.common.CommonCode;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.Dict;
import com.pmcc.onlineexam.service.DictService;
import com.pmcc.onlineexam.utils.GetUser;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    DictService dictService;

    @Autowired
   GetUser getUser;

  @GetMapping("/getdict")
  public List<Dict> getdict(){
    List<Dict> all = dictService.getdict();
    return all;
  }

  @GetMapping("/get-dictinfo-all")
    public List<Dict> getall(){
      List<Dict> all = dictService.getall();
      return all;
  }

  @PostMapping("/updatedict")
    public  String updatedict(@RequestBody LinkedHashMap map){
      System.out.println(map.toString());
      Dict dict=dictService.findById((String) map.get("id"));
      dict.setName((String) map.get("name"));
      dict.setValue((String) map.get("value"));
      dict.setSort((Integer) map.get("sort"));
      dict.setMemo((String) map.get("memo"));
      dict.setUpdated_by(getUser.getUsername().getId());
      dict.setUpdated_time(new Date());

      dictService.update(dict);
      return "ok";
  }

  /**
   * @author:添加一个工种
   * @description: TODO
   * @date: 2021-05-29 9:55
   * @param “”工种对象
   * @return
   */
  @PostMapping("/adddict")
  public  String adddict(@RequestBody Dict dict){
    try {
      dict.setStatus(CommonCode.STATUS_NORMAL);
      dict.setCreated_by(getUser.getUsername().getId());
      dict.setCreated_time(new Date());

      dictService.save(dict);
      return "ok";
    }catch (Exception e){
      e.printStackTrace();
    }
     return "no";
  }

  /**
   * @author: 删除一个工种
   * @description: TODO
   * @date: 2021-05-29 9:56
   * @param "id
   * @return
   */
  @GetMapping("deletedict")
  public void deletedict(String id){
    dictService.deleteid(id);
  }


  @GetMapping("/forbiddendict")
  public void forbiddendict(String id){
    Dict dict= dictService.findById(id);
    dict.setStatus(CommonCode.STATUS_DISABLE);
    dictService.update(dict);
  }
  @GetMapping("/startdict")
  public void startdict(String id){
    Dict dict=dictService.findById(id);
    dict.setStatus(CommonCode.STATUS_NORMAL);
    dictService.update(dict);
  }

  /**
   * @author: 模糊查询
   * @description: TODO
   * @date: 2021-05-29 11:00
   * @param
   * @return
   */
  @GetMapping("/getlike")
  public List<Dict> getlike(String like){
    return dictService.getlike(like);
  }

}
