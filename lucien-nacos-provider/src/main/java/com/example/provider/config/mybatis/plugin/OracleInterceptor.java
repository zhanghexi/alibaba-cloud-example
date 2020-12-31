package com.example.provider.config.mybatis.plugin;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 * @ClassName OracleInterceptor
 * @User zhang
 * @Description SQL拦截器配置
 * @Author Lucien
 * @Date 2020/8/20 1:25
 * @Version 1.0
 */
@Log4j2
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class OracleInterceptor implements Interceptor {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 拦截器核心实现
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] queryArgs = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) queryArgs[0];
        Object paramObj = null;
        if (queryArgs.length > 1) {
            paramObj = queryArgs[1];
        }
        long start = System.nanoTime();
        Object result = invocation.proceed();
        long end = System.nanoTime();
        long timing = end - start;

        /*设定过滤SQL语句的流程*/
        /*方法名*/
        String methodName = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(paramObj);
        Configuration configuration = mappedStatement.getConfiguration();
        /*格式化sql*/
        String sql = formatSql(boundSql, paramObj, configuration);
        log.info("\n具体方法名：" + methodName + "\n完整的SQL：" + sql + "\nSQL执行时间：" + (timing / 1000 / 1000) + "ms");
        return result;
    }

    private String formatSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s\n ]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (parameterMappings != null) {
            for (ParameterMapping parameterMapping : parameterMappings) {
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    sql = replacePlaceholder(sql, value);
                }
            }
        }
        return sql;
    }

    private String replacePlaceholder(String sql, Object value) {
        String resultSQL = "";
        if (value != null) {
            if (value instanceof String) {
                resultSQL = "'" + value + "'";
            } else if (value instanceof Date) {
                resultSQL = "'" + DateFormatUtils.format((Date) value, DATE_FORMAT) + "'";
            } else {
                resultSQL = value.toString();
            }
        } else {
            resultSQL = "null";
        }
        String quoteReplacement = Matcher.quoteReplacement(resultSQL);
        String sqlResult = sql.replaceFirst("\\?", quoteReplacement);
        return sqlResult;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }
}