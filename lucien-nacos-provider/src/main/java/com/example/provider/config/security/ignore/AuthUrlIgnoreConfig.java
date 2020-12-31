package com.example.provider.config.security.ignore;

import cn.hutool.core.util.ReUtil;
import com.example.provider.config.security.annotation.AuthIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @ClassName AuthUrlIgnoreConfig
 * @User zhang
 * @Description 忽略认证URL配置
 * @Author Lucien
 * @Date 2020/12/11 11:32
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class AuthUrlIgnoreConfig implements InitializingBean {

    @Resource
    private ApplicationContext applicationContext;
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");
    private static final String ASTERISK = "*";

    @Getter
    @Setter
    private List<String> urls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(mappingInfo -> {
            HandlerMethod handlerMethod = map.get(mappingInfo);
            AuthIgnore method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), AuthIgnore.class);
            Optional.ofNullable(method)
                    .ifPresent(authIgnore -> mappingInfo
                            .getPatternsCondition()
                            .getPatterns()
                            .forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, ASTERISK))));
            AuthIgnore controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), AuthIgnore.class);
            Optional.ofNullable(controller)
                    .ifPresent(authIgnore -> mappingInfo
                            .getPatternsCondition()
                            .getPatterns()
                            .forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, ASTERISK))));
        });
    }
}