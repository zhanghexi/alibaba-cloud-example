package com.example.provider.config.security;

import com.example.provider.config.security.handler.UserLogoutSuccessHandler;
import com.example.provider.config.security.ignore.AuthUrlIgnoreConfig;
import com.example.provider.config.security.provider.InnerAuthenticationProvider;
import com.example.provider.service.auth.Oauth2UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @ClassName WebSecurityConfig
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/1 17:00
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
/** 开启security注解*/
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private Oauth2UserDetailsService oauth2UserDetailsService;
    @Resource
    private UserLogoutSuccessHandler userLogoutSuccessHandler;
    @Resource
    private AuthUrlIgnoreConfig authUrlIgnoreConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(oauth2UserDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(innerAuthenticationProvider());
    }

    /**
     * url认证配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
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

    @Bean
    public InnerAuthenticationProvider innerAuthenticationProvider() {
        InnerAuthenticationProvider innerAuthenticationProvider = new InnerAuthenticationProvider();
        innerAuthenticationProvider.setUserServiceDetail(oauth2UserDetailsService);
        return innerAuthenticationProvider;
    }
}