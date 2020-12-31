package com.example.provider.config.security.provider;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName InnerAuthenticationToken
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/8 0:40
 * @Version 1.0
 */
public class InnerAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public InnerAuthenticationToken(String mobile) {
        super(null);
        this.principal = mobile;
        this.setAuthenticated(false);
    }

    public InnerAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a " +
                    "GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }
}
