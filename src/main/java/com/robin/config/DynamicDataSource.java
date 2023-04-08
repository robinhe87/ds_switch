package com.robin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author robin
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 获取数据源标识
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return LookupKeyContext.getDataSource();
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = super.getConnection();
        } catch (Exception e) {
            log.info("当前数据源:{}出现错误",LookupKeyContext.getDataSource());
            LookupKeyContext.swithDataSource();
            log.info("切换新数据源:{}",LookupKeyContext.getDataSource());
            connection = super.getConnection();
        }finally {
            LookupKeyContext.clear();
        }
        return connection;
    }

}