package com.example.provider.config.mybatis;

import com.example.provider.config.mybatis.plugin.ConfigUtils;
import lombok.Data;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @ClassName SqlSessionFactory
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/8/20 0:58
 * @Version 1.0
 */
@Data
@Configuration
@ConditionalOnClass(DataSource.class)
public class SqlSessionFactory {

    @Bean
    public SqlSessionFactoryBean dataSourceSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        ConfigUtils.getPlugins(factoryBean);
        return factoryBean;
    }
}
