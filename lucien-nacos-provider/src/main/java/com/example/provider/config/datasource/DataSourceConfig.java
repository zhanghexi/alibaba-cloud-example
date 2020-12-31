package com.example.provider.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName DataSourceConfig
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/8/29 23:04
 * @Version 1.0
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource", destroyMethod = "close")
    @Qualifier("dataSource")
    @Primary
    @RefreshScope
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource dataSource() {
        return new HikariDataSource();
    }
}
