package com.robin.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author robin
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 主数据源
     *
     * @return
     */
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 从数据源
     *
     * @return
     */
    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态配置数据源
     *
     * @param master
     * @param slave
     * @return
     */
    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource(@Qualifier("master") DataSource master,
                                 @Qualifier("slave") DataSource slave) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceTypeEnum.MASTER.name(), master);
        targetDataSources.put(DataSourceTypeEnum.SLAVE.name(), slave);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 设置默认使用的数据源，当没有从库可用时，使用主库
        dynamicDataSource.setDefaultTargetDataSource(master);
        return dynamicDataSource;
    }

    /**
     * 配置 MybatisPlus 的 SqlSessionFactoryBean
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

}