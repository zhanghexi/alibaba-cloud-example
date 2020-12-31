package com.example.provider.service.impl;

import com.example.provider.data.mapper.AuthRoleMapper;
import com.example.provider.data.model.AuthRole;
import com.example.provider.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName RoleServiceImpl
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/7 16:43
 * @Version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private AuthRoleMapper authRoleMapper;

    @Override
    public Set<String> getRolesByUserId(Integer id) {
        Set<String> roleSet = new HashSet<>();
        List<AuthRole> authRoles = authRoleMapper.findRoleByUserId(id);
        for (AuthRole role : authRoles) {
            roleSet.addAll(Arrays.asList(role.getRoleName().split(",")));
        }
        return roleSet;
    }
}