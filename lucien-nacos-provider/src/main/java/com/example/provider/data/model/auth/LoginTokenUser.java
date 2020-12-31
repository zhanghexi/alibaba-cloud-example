package com.example.provider.data.model.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @ClassName LoginTokenUser
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/1 18:06
 * @Version 1.0
 */
public class LoginTokenUser extends User implements Serializable {

    @Getter
    private Integer id;

    public LoginTokenUser(Integer id, String username, String password, boolean enabled, boolean accountNonExpired,
                          boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }
}