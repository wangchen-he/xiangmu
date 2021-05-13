package com.pmcc.onlineexam.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pmcc.core.security.controller.LoginController;
import com.pmcc.onlineexam.model.ExamAudit;
import com.pmcc.onlineexam.service.ExamAuditService;
import com.pmcc.system.model.SysUser;
import com.pmcc.system.oss.WebMvcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/exam/useraudit")
public class ExamAuditController {

    @Autowired
    ExamAuditService auditService;
    @RequestMapping("/getua")
    public List<ExamAudit> getua(String data) {
        SysUser user=auditService.getUsername();
        System.out.println("id"+user.getId());

        if (data.equals("")) {
            List<ExamAudit> all = auditService.findAll();
            return all;
        }
       Map<String, Object> map=new HashMap<String,Object>();
        map.put("depid",data);
        List<ExamAudit> all=auditService.findByField(map);
       return all;
    }

    @PostMapping("/userdictpass")
    public  String userdictpass(@RequestBody LinkedHashMap<String, String> map) {
        String da=map.get("data");
        auditService.userauditpass(da);
        return "A";
    }

    @PostMapping("/userdictno")
    public  String userdictno(@RequestBody LinkedHashMap<String, String> map){
        String data=map.get("data");
        auditService.userdictno(data);

        return "1";

    }


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request)  {
        if (file.isEmpty()){
            return "未选择文件";
        }

      String filePath ="E:/upImages/noticethumbnail/";
        File temp=new File(filePath);
        if (!temp.exists()){
            temp.mkdirs();
        }

        long date=new Date().getTime();
        File upfile=new File(filePath+date+".png");
        try{
            file.transferTo(upfile);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("上传失败");
        }



//        //上传文件保存路径
//        String path = request.getServletContext().getRealPath("/upload");
//        File realPath = new File(path);
//        if (!realPath.exists()){
//            realPath.mkdir();
//        }
//
//        //transferTo：将文件写入到磁盘，参数就是一个文件
//        file.transferTo(new File(realPath+"/"+file.getOriginalFilename()));

        return date+".png";
    }

/**

    //文件上传：流
    @RequestMapping("/uploadtext")
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        //1.获得文件名
        String filename = file.getOriginalFilename();
        if ("".equals(filename)){
            return "文件不存在";
        }
        //2.上传文件保存路径
        String path = request.getServletContext().getRealPath("/upload");
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        //3.上传文件
        InputStream is = file.getInputStream();
        FileOutputStream os = new FileOutputStream(new File(realPath, filename));

        int len = 0;
        byte[] buffer =  new byte[1024];

        while ((len=is.read(buffer))!=-1){
            os.write(buffer,0,len);
            os.flush();
        }

        //4.关闭流
        os.close();
        is.close();
        return "上传完毕";
    }
**/
}

