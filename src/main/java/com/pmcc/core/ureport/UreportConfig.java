package com.pmcc.core.ureport;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.servlet.Servlet;

/**
 * @Classname UreportConfig
 * @Description TODO UreportConfig内置数据源
 * @Version 1.0
 */
@Configuration
@ImportResource("classpath:ureport-console-context.xml")
public class UreportConfig {


    /**
     * TODO 测试 20201113 报表工具UREPORT
     * 报表工具配置
     *
     * MyUReportPropertyConfigurer
     */
    @Bean
    public ServletRegistrationBean<Servlet> ureportServlet(){
        return new ServletRegistrationBean<Servlet>(new UReportServlet(),"/ureport/*");
    }

}
