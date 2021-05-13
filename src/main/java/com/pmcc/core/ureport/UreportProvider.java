package com.pmcc.core.ureport;

import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.system.model.SysUreport;
import com.pmcc.system.service.SysUreportService;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname UreportProvider
 * @Description TODO 报表功能
 * @Version 1.0
 */
@Setter
@Component
@ConfigurationProperties(prefix = "ureport")
public class UreportProvider implements ReportProvider {

    // 存储器名称
    private static final String NAME = "sqlserver-provider";

    // 特定前缀，ureport底层会调用 getPrefix 方法来获取报表操作的Provier类
    //private String prefix = "sqlserver:";
    private String prefix;

    //文件存储位置
    //private String fileStoreDir ="D:/ureportfiles";
    @Value("${ureport.fileStoreDir}")
    private String fileStoreDir;
    //private String fileStoreDir = "/WEB-INF/ureportfiles/";

    //是否禁用
    private boolean disabled =false;


    @Autowired
    private SysUreportService sysUreportService;

    @Resource
    private JwtUserService jwtUserService;

    /**
     * 根据报表名加载报表文件
     * @param file 报表名称
     * @return 返回的InputStream
     */
    @Override
    public InputStream loadReport(String file) {
        SysUreport sysUreport=new SysUreport();
        if (file.endsWith(".xml")){
            String ureportName=file.substring(0,file.indexOf('.'));
            sysUreport=sysUreportService.findByName(getCorrectName(ureportName));
        }else {

            sysUreport = sysUreportService.findByName(getCorrectName(file));
        }
        String content = sysUreport.getContent();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
        return inputStream;
        //return null;
    }

    /**
     * 根据报表名，删除指定的报表文件
     * @param str 报表名称
     */
    @Override
    public void deleteReport(@RequestBody String str) {
            sysUreportService.deleteByID(str);
    }
    /**
     * 获取所有的报表文件
     * @return 返回报表文件列表
     */
    @Override
    public List<ReportFile> getReportFiles() {
        List<SysUreport> list = sysUreportService.findUAll();
        List<ReportFile> reportList = new ArrayList<>();
        for (SysUreport ureport : list) {
            reportList.add(new ReportFile(ureport.getUreportName(),ureport.getUpdateTime()));
        }
        return reportList;
    }

    /**
     * 保存报表文件
     * @param //file 报表名称
     * @param //content 报表的xml内容
     */
    @Override
    public void saveReport(String file, String content) {
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        SysUreport sysUreport=new SysUreport();
        if (file.startsWith(this.prefix)) {
            file = file.substring(this.prefix.length(), file.length());
        }
        String fullPath = this.fileStoreDir + "/" + file;
        FileOutputStream outStream = null;

        try {
            outStream = new FileOutputStream(new File(fullPath));
            IOUtils.write(content, outStream, "utf-8");
        } catch (Exception var13) {
            throw new ReportException(var13);
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }
        }

        file = getCorrectName(file);
        if (file.endsWith(".xml")){
            String ureportName=file.substring(0,file.indexOf('.'));
            sysUreport = sysUreportService.findByName(ureportName);
            //System.out.println(ureportName);

            //SysUser user = jwtUserService.onLineUser();
            if (sysUreport == null){

                sysUreport=new SysUreport();
                sysUreport.setUreportName(ureportName);
                sysUreport.setFileName(file);
                sysUreport.setContent(content);
                //sysUreport.setCreator(user);
                sysUreport.setCreateTime(timenow);
                //sysUreport.setOperator(user);

                sysUreport.setUpdateTime(timenow);

                sysUreportService.save(sysUreport);
            }

        }else{

            sysUreport.setUreportName(file);
            sysUreport.setFileName(file+".ureport.xml");
            //sysUreport.setFileName(file);
            sysUreport.setContent(content);
            //sysUreport.setCreator(user);
            sysUreport.setCreateTime(timenow);
            //sysUreport.setOperator(user);
            sysUreport.setUpdateTime(timenow);

            sysUreportService.update(sysUreport);
        }
    }

    /**
     * @return 返回存储器名称
     */
    @Override
    public String getName() {
        return NAME;
    }
    /**
     * @return 返回是否禁用
     */
    @Override
    public boolean disabled() {
        return disabled;
    }
    /**
     * @return 返回报表文件名前缀
     */
    @Override
    public String getPrefix() {
        return prefix;
    }

    /**
     * @description 获取没有前缀的文件名
     * @param name
     * @return
     */

    private String getCorrectName(String name){

        if(name.startsWith(prefix)){
            name = name.substring(prefix.length(), name.length());
        }
        return name;
    }

    public void setFileStoreDir(String fileStoreDir) {
        this.fileStoreDir = fileStoreDir;
    }

    public String getFileStoreDir() {
        return fileStoreDir;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        File file = new File(fileStoreDir);
        if (!file.exists()) {
            if (applicationContext instanceof WebApplicationContext) {
                WebApplicationContext context = (WebApplicationContext)applicationContext;
                ServletContext servletContext = context.getServletContext();
                String basePath = servletContext.getRealPath("/");
                this.fileStoreDir = basePath + this.fileStoreDir;
                file = new File(this.fileStoreDir);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
    }
}
