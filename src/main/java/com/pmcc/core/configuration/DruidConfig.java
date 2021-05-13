//package com.darkbf.core.configuration;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * @ClassName: DruidConfig <br>
// * @Description: Druid数据源配置
// * @Date: 2019/12/8 16:00 <br>
// * @Author: DarkBF <br>
// * @Version: 1.0 <br>
// */
//@Configuration
//public class DruidConfig {
//
//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    public DataSource druid(){
//        return  new DruidDataSource();
//    }
//}
