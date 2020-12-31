package com.example.provider.config.security;

import com.example.provider.config.security.converter.CustomUserAuthenticationConverter;
import com.example.provider.config.security.handler.CustomAccessDeniedHandler;
import com.example.provider.config.security.handler.CustomAuthenticationEntryPoint;
import com.example.provider.config.security.handler.UserLogoutSuccessHandler;
import com.example.provider.config.security.ignore.AuthUrlIgnoreConfig;
import com.example.provider.config.security.redis.RedisTokenStoreConfig;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import javax.annotation.Resource;

/**
 * @ClassName ResourcesServerConfig
 * @User zhang
 * @Description 资源服务器配置
 * @Author Lucien
 * @Date 2020/12/1 19:53
 * @Version 1.0
 */
@Configuration
@EnableResourceServer
public class ResourcesServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private CustomAuthenticationEntryPoint authExceptionEntryPoint;
    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Resource
    private RedisTokenStoreConfig redisTokenStoreConfig;
    @Resource
    private ResourceServerProperties resourceServerProperties;
    @Resource
    private OAuth2ClientProperties oAuth2ClientProperties;
    @Resource
    private CustomUserAuthenticationConverter customUserAuthenticationConverter;
    @Resource
    private UserLogoutSuccessHandler userLogoutSuccessHandler;
    @Resource
    private AuthUrlIgnoreConfig authUrlIgnoreConfig;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        /*配置数据库里面的resource_id*/
        resources.resourceId("project_api").stateless(true);
        /*配置从redis获取token的方法*/
        resources.tokenStore(redisTokenStoreConfig.redisTokenStore());
        /*配置自定义权限异常*/
        resources.authenticationEntryPoint(authExceptionEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .tokenServices(tokenServices());
    }

    /**
     * 自定义: ResourceServerTokenServices 资源服务器自行验证签名并解析令牌
     *
     * @return
     */
    @Bean
    public ResourceServerTokenServices tokenServices() {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(customUserAuthenticationConverter);

        remoteTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
        remoteTokenServices.setClientId(oAuth2ClientProperties.getClientId());
        remoteTokenServices.setClientSecret(oAuth2ClientProperties.getClientSecret());

        remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        return remoteTokenServices;
    }

    /**
     * 资源路径认证配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*获取要忽略鉴权的url数组*/
        String[] urls = authUrlIgnoreConfig.getUrls().stream().distinct().toArray(String[]::new);
        /*增加自定义登出url和handler*/
        http.logout().logoutUrl("/dev-api/logout").logoutSuccessHandler(userLogoutSuccessHandler);
        /*接口身份认证*/
        http.authorizeRequests()
                /*只有登录、生成验证码接口不用鉴权*/
                .antMatchers(urls).permitAll()
                /*其他的url请求都鉴权*/
                .anyRequest().authenticated()
                /*关闭csrf*/
                .and().csrf().disable();
    }
}