package com.pmcc.onlineexam.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pmcc.core.security.controller.LoginController;
import com.pmcc.onlineexam.model.ExamAudit;
import com.pmcc.onlineexam.model.ExamUserPicture;
import com.pmcc.onlineexam.service.ExamAuditService;
import com.pmcc.onlineexam.service.ExamUserPictureService;
import com.pmcc.onlineexam.utils.GetUser;
import com.pmcc.system.common.CommonCode;
import com.pmcc.system.model.SysDictionaries;
import com.pmcc.system.model.SysUser;
import com.pmcc.system.oss.WebMvcConfiguration;
import com.pmcc.system.service.SysDictionariesService;
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
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/exam/useraudit")
public class ExamAuditController {

    @Autowired
    ExamAuditService auditService;
    @Autowired
    ExamUserPictureService examUserPictureService;

    @Autowired
    GetUser getUser;
    @RequestMapping("/getua")
    public List<ExamAudit> getua(String data) {
        SysUser user=auditService.getUsername();
        System.out.println("id"+user.getId());

        if (data.equals("")) {
            List<ExamAudit> all = auditService.getall();
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

   @GetMapping("/deleteuser")
   public String deleteuser(String id){
       System.out.println(id);
        auditService.deleteid(id);
        return "ok";
   }
    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile file,String image_id, HttpServletResponse response,HttpServletRequest req)  {
        System.out.println("开始上传");

        if (file.isEmpty()){
            return null;
        }

      String filePath ="E:/upImages/noticethumbnail/";
        File temp=new File(filePath);
        if (!temp.exists()){
            temp.mkdirs();
        }

        long date=new Date().getTime();

        String fileName0 = file.getOriginalFilename();  // 文件名
        String zui = fileName0.substring(fileName0.lastIndexOf("."));  // 后缀名
        File upfile=new File(filePath+date+zui);
        try{
            file.transferTo(upfile);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("上传失败");
            return null;
        }


        ///////写入数据库#################################
        SysUser user=getUser.getUsername();

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        ExamUserPicture image=new ExamUserPicture();
        image.setPic_name(fileName);
        image.setFile_name(date+"");
        image.setPic_url(filePath+date+suffixName);
        image.setPic_type(suffixName);

        if("".equals(image_id)||image_id==null){
            image.setCreated_time(new Date());
            image.setCreated_by(user.getId());
            ExamUserPicture save = examUserPictureService.save(image);

            Map<String,Object> map=new HashMap<>();
            map.put("url","http://localhost:8080/image/"+date+suffixName);
            map.put("id",save.getId());
            return map;
        }else {

            ExamUserPicture lodimage=examUserPictureService.findById(image_id);
            image.setId(image_id);
            //更新照片
            image.setCreated_time(lodimage.getCreated_time());
            image.setCreated_by(lodimage.getCreated_by());
            image.setUpdated_by(user.getId());
            image.setUpdated_time(new Date());
            ExamUserPicture save= examUserPictureService.update(image);
            Map<String,Object> map=new HashMap<>();
            map.put("url","http://localhost:8080/image/"+date+suffixName);
            map.put("id",save.getId());
            return map;
        }

    }


    @GetMapping("/getimagebuid")
    public  ExamUserPicture getimagebuid(String id){
        ExamUserPicture im= examUserPictureService.findById(id);
        return im;
    }

    @Autowired
    SysDictionariesService getdivt;

    @GetMapping("/getdict")
    public  List<SysDictionaries> getdict(){


        List<SysDictionaries> list= getdivt.getSysDictionariesByParentID("204028824c01769d16eb370021","0");
        return list;
    }

    //添加用户
    @PostMapping("/adduser")
    public  String adduser(@RequestBody LinkedHashMap<String, String> map){
        String data=JSON.toJSONString(map);

       ExamAudit user= JSON.parseObject(data,ExamAudit.class);
       //添加创建时间
       user.setCreated_time(new Date());
       SysUser ause=getUser.getUsername();
       //添加创建用户的id
       user.setCreated_by(ause.getId());

       //管理员创建用户时无需审核
        user.setState(CommonCode.AUDITO);
        user.setUser_status(CommonCode.STATUS_NORMAL);
        auditService.save(user);
        System.out.println(user);
        System.out.println(map.toString());
        return "ok";
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

