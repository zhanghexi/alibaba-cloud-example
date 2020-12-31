package com.example.provider.config.security;

import com.example.provider.config.security.enhancer.CustomTokenEnhancer;
import com.example.provider.config.security.exception.Oauth2WebResponseExceptionTranslator;
import com.example.provider.config.security.redis.RedisTokenStoreConfig;
import com.example.provider.service.auth.Oauth2UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @ClassName AuthorizationServerConfig
 * @User zhang
 * @Description 授权服务器配置
 * @Author Lucien
 * @Date 2020/12/1 19:34
 * @Version 1.0
 */
@Configuration
/** 开启验证服务器*/
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private DataSource dataSource;
    @Resource
    private Oauth2UserDetailsService oauth2UserDetailsService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisTokenStoreConfig redisTokenStoreConfig;
    @Resource
    private CustomTokenEnhancer customTokenEnhancer;
    @Resource
    private Oauth2WebResponseExceptionTranslator oauth2WebResponseExceptionTranslator;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        /*在此配置的client-id和client_secret分别为 user_client 和 user_client_secret*/
        JdbcClientDetailsService detailsService = new JdbcClientDetailsService(dataSource);
        detailsService.setPasswordEncoder(passwordEncoder());
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置认证管理器
        endpoints.authenticationManager(authenticationManager)
                /*用户账号、密码认证*/
                .userDetailsService(oauth2UserDetailsService)
                /*指定token存储位置*/
                .tokenStore(redisTokenStoreConfig.redisTokenStore())
                /*指定自定义令牌规则*/
                .tokenEnhancer(customTokenEnhancer)
                /*自定义oauth2鉴权url地址（也可作为登录地址）*/
                .pathMapping("/oauth/token", "/dev-api/oauth/token")
                /*增加异常认证处理器*/
                .exceptionTranslator(oauth2WebResponseExceptionTranslator);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*设置客户端的配置从数据库中读取，存储在oauth_client_details表*/
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * 授权服务安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /*允许表单认证，这一行必须配置，采用post表单提交用到(配置这一步可以在postman请求token的时候
        不用在Authorization选项栏中设置Basic Auth选项)
        参考文献：https://blog.csdn.net/weixin_32445909/article/details/106733479*/
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("permitAll()");
        /*允许 check_token 访问（这里必须设为permitAll才能生效），调用底层CheckTokenEndpoint的checkToken方法
        （对应的路径/oauth/check_token）具体路径：http://localhost:8083/oauth/check_token?token=a9c57cbc-ef41-4129-a3cc-4997b5911029*/
        security.checkTokenAccess("permitAll()");
    }
}