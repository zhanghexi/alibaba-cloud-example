package com.example.provider.config.security.converter;

import cn.hutool.core.convert.Convert;
import com.example.provider.data.model.auth.LoginTokenUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName CustomUserAuthenticationConverter
 * @User zhang
 * @Description 正确返回用户鉴权信息、以及principal对象
 * @Author Lucien
 * @Date 2020/12/7 20:57
 * @Version 1.0
 */
@Component
public class CustomUserAuthenticationConverter implements UserAuthenticationConverter {

    private static final String N_A = "N/A";

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());

        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getUserAuthorities(map);

            Integer userId = Convert.toInt(map.get("user_id"));
            String username = (String) map.get("username");
            LoginTokenUser user = new LoginTokenUser(userId, username, N_A, true, true, true, true, authorities);
            return new UsernamePasswordAuthenticationToken(user, N_A, authorities);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getUserAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        return AuthorityUtils.NO_AUTHORITIES;
    }
}