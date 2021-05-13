package com.pmcc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Classname ServletInitializer
 * @Description TODO
 * @Version 1.0
 */

/**
 * 继承SpringBootServletInitializer方可正常部署到常规的tomcat，相当于web.xml作用。
 * 参考 https://www.bilibili.com/video/av38657363?p=51
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OnlineExamServiceApplication.class);
    }

}
