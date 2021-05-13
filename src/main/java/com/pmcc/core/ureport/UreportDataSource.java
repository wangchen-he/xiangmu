package com.pmcc.core.ureport;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Classname UreportDataSource
 * @Description TODO
 * @Version 1.0
 */
@Component
public class UreportDataSource implements BuildinDatasource {

    private Logger log = LoggerFactory.getLogger(UreportConfig.class);

    private static final String NAME = "InformationManagementOfRetirees";

    @Autowired
    private DataSource dataSource;

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Connection getConnection() {

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("Ureport数据源，获取连接失败！");
            e.printStackTrace();
        }

        return null;
    }
}
