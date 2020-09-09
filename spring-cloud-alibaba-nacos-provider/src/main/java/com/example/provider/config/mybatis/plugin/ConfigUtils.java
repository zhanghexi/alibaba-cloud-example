package com.example.provider.config.mybatis.plugin;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;

import java.util.Properties;

/**
 * @ClassName ConfigUtils
 * @User zhang
 * @Description 配置分页插件
 * @Author Lucien
 * @Date 2020/8/20 1:08
 * @Version 1.0
 */
public class ConfigUtils {

    public static void getPlugins(SqlSessionFactoryBean sqlSessionFactoryBean) {
        /*PageHelper 5.1版本写法(指向PageInterceptor实现类)*/
        Interceptor pageHelper = new PageInterceptor();
        Properties properties = new Properties();
        /*PageHelper插件5.0.0以后的版本支持自动识别使用的数据库，可以不用配置 <property name="dialect" value="oracle"/>*/
        /*或者参考：
        https://mp.weixin.qq.com/s?__biz=MzAxODcyNjEzNQ==&mid=2247499595&idx=3&sn=aa6e182776de53fa686efbe628928751&chksm=
        9bd352d3aca4dbc574f7cc985632662c780e876317a5a4db42fdb5f06ec74989bd08366c9fee&scene=21#wechat_redirect
        进行如下配置*/
        properties.setProperty("helperDialect", "oracle");
        properties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper, new OracleInterceptor()});
    }
}
