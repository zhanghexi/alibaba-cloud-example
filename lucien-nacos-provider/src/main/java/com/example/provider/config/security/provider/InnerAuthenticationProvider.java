package com.example.provider.config.security.provider;

import com.example.provider.service.auth.Oauth2UserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;

/**
 * @ClassName InnerAuthenticationProvider
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/8 0:36
 * @Version 1.0
 */
public class InnerAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private Oauth2UserDetailsService oauth2UserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        InnerAuthenticationToken authenticationToken = (InnerAuthenticationToken) authentication;
        //校验username
        UserDetails user = oauth2UserDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        //认证成功
        InnerAuthenticationToken authenticationResult = new InnerAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    public void setUserServiceDetail(Oauth2UserDetailsService userDetailsService) {
        this.oauth2UserDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return InnerAuthenticationToken.class.isAssignableFrom(authentication);
    }
}