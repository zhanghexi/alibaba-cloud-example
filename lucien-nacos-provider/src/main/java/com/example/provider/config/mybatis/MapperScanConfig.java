package com.example.provider.config.mybatis;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * @ClassName MapperScanConfig
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/8/20 0:55
 * @Version 1.0
 */
@Data
@Configuration
public class MapperScanConfig {

    /**
     * 该方法如果不加static的话会出现：
     * Cannot enhance @Configuration bean definition 'xxx' since its singleton instance has been created too early的提示
     * 详见：https://blog.csdn.net/u013202238/article/details/90315764
     *
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        /*定义要扫描的包路径*/
        configurer.setBasePackage("com.example.provider.data.mapper");
        Properties properties = new Properties();
        properties.setProperty("mappers", "tk.mybatis.mapper.common.Mapper");
        configurer.setProperties(properties);
        configurer.setSqlSessionFactoryBeanName("dataSourceSessionFactory");
        return configurer;
    }
}
