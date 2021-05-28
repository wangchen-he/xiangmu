package com.pmcc.onlineexam.controller;

import com.alibaba.fastjson.JSON;
import com.pmcc.onlineexam.common.CommonCode;
import com.pmcc.onlineexam.model.ExamQuestionBank;
import com.pmcc.onlineexam.service.ExamQuestionBankService;
import com.pmcc.onlineexam.utils.GetUser;
import com.pmcc.system.model.SysDictionaries;
import org.apache.commons.collections4.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.*;

@RestController
@RequestMapping("/exam/question")
public class ExamQuestionBankController {
    @Autowired
    ExamQuestionBankService bankService;
    @Autowired
    GetUser user;

    @GetMapping("/getall")
    public List<ExamQuestionBank> getall() {
        return bankService.getall();
    }

    /**
     * @param “表单数据
     * @return
     * @author:
     * @description: 添加试卷
     * @date: 2021-05-18 15:19
     */
    @PostMapping("/postdata")
    public String postdata(@RequestBody ExamQuestionBank examQuestionBank) {
        String userid = user.getUsername().getId();
        examQuestionBank.setCreated_by(userid);
        examQuestionBank.setCreated_time(new Date());
        examQuestionBank.setChstatus(CommonCode.UN_AUDITO);
        examQuestionBank.setStatus(CommonCode.STATUS_NORMAL);

        System.out.println(examQuestionBank);
        bankService.save(examQuestionBank);
        return "ok";
    }

    /**
     * @param "id
     * @return
     * @author:
     * @description: 审核试题
     * @date: 2021-05-18 15:45
     */
    @GetMapping("auto")
    public void auto(String list, String tf) {
        String lists = GetUser.getStringli(list);
        bankService.auto(lists, tf);

    }

    /**
     * @param "id
     * @return
     * @author:
     * @description: 删除用户
     * @date: 2021-05-18 16:19
     */
    @GetMapping("deletequ")
    public void deletequ(String id) {
        bankService.deletequ(id);
    }

    /**
     * @param “null
     * @return
     * @author: 条件查询
     * @description: TODO
     * @date: 2021-05-18 16:21
     */
    @GetMapping("getlike")
    public List<ExamQuestionBank> getlike(String data) {
        List<ExamQuestionBank> list = bankService.getlike(data);
        return list;
    }

    @PostMapping("updetedata")
    public void updetedata(@RequestBody LinkedHashMap<String, String> map) {
        System.out.println(map);
        ExamQuestionBank upban = bankService.findById(map.get("id"));
        upban.setTest_content(map.get("test_content"));
        upban.setDifficult(map.get("difficult"));
        upban.setRight_answer(map.get("right_answer"));
        upban.setDict_id(map.get("dict_id"));
        upban.setUpdated_by(user.getUsername().getId());
        upban.setUpdated_time(new Date());
        if ("0".equals(map.get("test_type"))) {
            upban.setOptionsA(map.get("optionsA"));
            upban.setOptionsB(map.get("optionsB"));
            upban.setOptionsC(map.get("optionsC"));
            upban.setOptionsD(map.get("optionsD"));
            upban.setOptionsE(map.get("optionsE"));
            bankService.update(upban);
        } else if ("1".equals(map.get("test_type"))) {
            upban.setOptionsA(map.get("optionsA"));
            upban.setOptionsB(map.get("optionsB"));
            upban.setOptionsC(map.get("optionsC"));
            upban.setOptionsD(map.get("optionsD"));
            upban.setOptionsE(map.get("optionsE"));
            upban.setOptionsF(map.get("optionsF"));
            bankService.update(upban);
        } else if ("2".equals(map.get("test_type"))) {

            bankService.update(upban);
        }

    }

    /**
     * @param “获取工种字典
     * @return
     * @author:
     * @description: TODO
     * @date: 2021-05-21 19:28
     */
    @GetMapping("getdict")
    public List<SysDictionaries> getdict() {
        return bankService.getdict();

    }

/**
 * @author:excel试题录入
 * @description: TODO
 * @date: 2021-05-27 19:01
 * @param "data
 * @return
 */
    @PostMapping("excelpost")
    public String excelpost(@RequestBody LinkedHashMap map) {
        System.out.println("==========");
        if (CommonCode.Judge.equals(map.get("pattern"))) {
            LinkedHashMap exdata = (LinkedHashMap) map.get("exceldata");

            ArrayList exdatalist = relist(exdata);
            ArrayList heaf = (ArrayList) exdatalist.get(0);
            if (heaf.size() != 3) {
                return "excel中不为选择题";
            } else {
                for (int i = 1; i < exdatalist.size(); i++) {
                    try {
                    ArrayList body = (ArrayList) exdatalist.get(i);
                    ExamQuestionBank data = new ExamQuestionBank();
                    data.setDict_id((String) map.get("exceldict"));  //工种
                    data.setTest_type(Integer.parseInt(CommonCode.Judge)); //题型
                    data.setTest_content((String) body.get(0)); //题目
                    //难度
                    if ("简单".equals(body.get(1))) {
                        data.setDifficult(CommonCode.Simpleness);
                    } else if ("普通".equals(body.get(1))) {
                        data.setDifficult(CommonCode.Ordinary);
                    } else if ("困难".equals(body.get(1))) {
                        data.setDifficult(CommonCode.Diff);
                    }else {
                        continue;
                    }
                    //答案
                    if ("错误".equals(body.get(2))) {
                        data.setRight_answer(CommonCode.Fl);
                    } else if ("正确".equals(body.get(2))) {
                        data.setRight_answer(CommonCode.Tr);
                    }else {
                        continue;
                    }
                    //状态
                    data.setStatus(CommonCode.STATUS_NORMAL);
                    data.setChstatus(CommonCode.UN_AUDITO);

                    data.setCreated_by(user.getUsername().getId());
                    data.setCreated_time(new Date());

                    bankService.save(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
            // List<String> list= JSON.parseArray(map.get("exceldata"),String.class);
        }else if(CommonCode.Radino.equals(map.get("pattern"))){
         ///////////////////////////单选
            LinkedHashMap exdata = (LinkedHashMap) map.get("exceldata");
            ArrayList exdatalist = relist(exdata);
            ArrayList heaf = (ArrayList) exdatalist.get(0);
            //表格长度
            int dsize=heaf.size();
            if(heaf.size()<6||heaf.size()>8){
                return "选项为3到5个";
            }else {
                for (int i = 1; i < exdatalist.size(); i++) {
                    try {
                    ArrayList body = (ArrayList) exdatalist.get(i);
                    ExamQuestionBank data = new ExamQuestionBank();
                    data.setDict_id((String) map.get("exceldict"));  //工种
                    data.setTest_type(Integer.parseInt(CommonCode.Radino)); //题型


                    data=saveti(data,body);
                    if(data==null){continue;}
                    //答案
                    data.setRight_answer(body.get(dsize-1).toString());

                    ///////////////////////选项

                     data.setOptionsA(body.get(2).toString());
                     data.setOptionsB(body.get(3).toString());
                    data.setOptionsC(body.get(4).toString());
                    if(dsize==8){
                        if(body.get(5)!=null)data.setOptionsD(body.get(5).toString());
                        if(body.get(6)!=null)data.setOptionsE(body.get(6).toString());
                    }else if(dsize==7){
                        if(body.get(5)!=null)data.setOptionsD(body.get(5).toString());
                    }

                    //////////////////////

                    //状态

                        bankService.save(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }else if(CommonCode.Multiple.equals(map.get("pattern"))){
            //////////////////多选
            LinkedHashMap exdata = (LinkedHashMap) map.get("exceldata");
            ArrayList exdatalist =relist(exdata);
            ArrayList heaf = (ArrayList) exdatalist.get(0);
            //表格长度
            int dsize=heaf.size();
            if(heaf.size()<7||heaf.size()>9){
                return "选项为4到6个";
            }else {
                for (int i = 1; i < exdatalist.size(); i++) {
                    try {
                    ArrayList body = (ArrayList) exdatalist.get(i);
                    ExamQuestionBank data = new ExamQuestionBank();
                    data.setDict_id((String) map.get("exceldict"));  //工种
                    data.setTest_type(Integer.parseInt(CommonCode.Multiple)); //题型


                    data=saveti(data,body);
                    if(data==null){continue;}
                    //答案
                    String daan=body.get(dsize-1).toString();
                    if(daan.length()>1){  data.setRight_answer(body.get(dsize-1).toString());}
                    else {continue;}


                    ///////////////////////选项

                     data.setOptionsA(body.get(2).toString());
                     data.setOptionsB(body.get(3).toString());
                     data.setOptionsC(body.get(4).toString());
                     data.setOptionsD(body.get(5).toString());
                    if(dsize==9){
                        if(body.get(6)!=null)data.setOptionsE(body.get(6).toString());
                        if(body.get(7)!=null)data.setOptionsF(body.get(7).toString());
                    }else if(dsize==8){
                        if(body.get(6)!=null)data.setOptionsE(body.get(6).toString());
                    }

                    //////////////////////

                    //状态

                        bankService.save(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        }
        return "上传成功";
    }

    public ExamQuestionBank saveti(ExamQuestionBank data,ArrayList body){
        data.setTest_content((String) body.get(0)); //题目
        //难度
        if ("简单".equals(body.get(1))) {
            data.setDifficult(CommonCode.Simpleness);
        } else if ("普通".equals(body.get(1))) {
            data.setDifficult(CommonCode.Ordinary);
        } else if ("困难".equals(body.get(1))) {
            data.setDifficult(CommonCode.Diff);
        }else {
            return null;
        }

        //状态
        data.setStatus(CommonCode.STATUS_NORMAL);
        data.setChstatus(CommonCode.UN_AUDITO);

        data.setCreated_by(user.getUsername().getId());
        data.setCreated_time(new Date());
        return  data;

    }
/**
 * @author: 得到全部的题的集合
 * @description: TODO
 * @date: 2021-05-27 19:37
 * @param "exceldata
 * @return
 */
    public ArrayList relist(LinkedHashMap exdata){
        Iterator<String> iter=exdata.keySet().iterator();
        ArrayList relist=new ArrayList();
        while (iter.hasNext()){
            String key=iter.next();
            ArrayList list= (ArrayList) exdata.get(key);
            relist.addAll(list);
        }

        return relist;
    }
}
