package com.example.userservice.config.durid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
@Configuration
@EnableConfigurationProperties(DruidDataSourceProperties.class)
public class DruidConfig {

    @Autowired
    private DruidDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource;
        try {
            //将我们自己定义的配置信息注入的DruidDataSource的对象中
            druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(properties.getDriverClassName());
            druidDataSource.setUrl(properties.getUrl());
            druidDataSource.setUsername(properties.getUsername());
            druidDataSource.setPassword(properties.getPassword());
            druidDataSource.setMaxActive(properties.getMaxActive());
            druidDataSource.setInitialSize(properties.getInitialSize());
            druidDataSource.setMaxWait(properties.getMaxWait());
            druidDataSource.setMinIdle(properties.getMinIdle());
            druidDataSource.setValidationQuery(properties.getValidationQuery());
            druidDataSource.setFilters(properties.getFilters());
            druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
            druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
            druidDataSource.setPoolPreparedStatements(properties.getPoolPreparedStatements());
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
            druidDataSource.setMaxOpenPreparedStatements(properties.getMaxOpenPreparedStatements());
            druidDataSource.init();
            return druidDataSource;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
