package com.pmcc.onlineexam.controller;


import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.onlineexam.entity.PageDto;
import com.pmcc.onlineexam.model.Dict;
import com.pmcc.onlineexam.service.DictService;
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
  @GetMapping("/get-dictinfo-all")
    public List<Dict> getall(){
      List<Dict> all = dictService.getall();
      return all;
  }


}
