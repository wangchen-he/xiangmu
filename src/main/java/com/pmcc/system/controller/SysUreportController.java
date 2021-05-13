package com.pmcc.system.controller;

/**
 * @Classname SysUreportController
 * @Description TODO
 * @Date 2021/1/5 17:01
 * @Created by yanglei
 * @Version 1.0
 */

import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.system.model.SysUreport;
import com.pmcc.system.service.SysUreportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sys/ureport")
public class SysUreportController {

    @Value("${ureport.fileStoreDir}")
    private String fileStoreDir;

    @Autowired
    private SysUreportService sysUreportService;

    /**
     * 获取所有的报表文件
     * @return 返回报表文件列表
     */
    @GetMapping("/ureport-get-all")
    public List<SysUreport> getAllUreport() {
        List<SysUreport> list = new ArrayList<>();
        list = sysUreportService.findAll();
        return list;
    }

    /**
     * 删除数据库以及文件夹报表文件
     * @param str
     * @return
     */
    @PostMapping("/ureport-del")
    public ApiResult delUreport(@RequestBody String str){

        if(str.length() == 0){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }

        String fileStoreDir="E:/ureportfiles";

        List<SysUreport> list = new ArrayList<>();

        File file=new File(fileStoreDir);

        File [] files=file.listFiles();   //listFiles方法：返回file路径下所有文件和文件夹的绝对路径

        String[] filelist = file.list();    //list 方法: 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录。

        SysUreport ureportById = sysUreportService.findById(str);
        String fileName = ureportById.getFileName();

        for (int i=0; i<files.length; i++){
            System.out.println(files[i]);
            System.out.println(filelist[i]);
            if (files[i].exists() && fileName.equals(filelist[i])){
                files[i].delete();
            }
        }

        String temp[]=str.split(",");
        for (int i = 0; i < temp.length; i++) {
            SysUreport sysUreport = sysUreportService.findById(temp[i]);
            sysUreportService.deleteByID(temp[i]);
            list.add(sysUreport);
        }

        if(list.size() == temp.length){
            return ApiResult.success();
        }else{
            return ApiResult.fail("删除失败");
        }
    }


/*    public static void deleteFiles(String fileNameOrPath){
        File file = new File(fileNameOrPath);
        File[] filesPath = file.listFiles();  //文件路径
        *//* 删除文件及文件夹 *//*
        for(int i=0; i<filesPath.length; i++) {
            if(filesPath[i].isDirectory()){
                File fileTemp = new File(filesPath[i].toString());
                File[] filesPathTemp = fileTemp.listFiles();  //文件路径
//              System.out.print(filesPath2.length);
                if(filesPathTemp.length>0){
                    deleteFiles(filesPath[i].toString());
                    filesPath[i].delete();
                }else{
                    if(filesPath[i].exists()){
                        filesPath[i].delete();
                    }
                }
                filesPath[i].delete();
            }else{
                if(filesPath[i].exists()){
                    filesPath[i].delete();
                }
            }
        }
    }*/


    @GetMapping("/ureport-getby-name")
    public SysUreport getUreByName(@RequestParam("ureportName")String ureportName){
        SysUreport sysUreport = new SysUreport();
        sysUreport=sysUreportService.findByName(ureportName);
        return sysUreport;
    }
}
