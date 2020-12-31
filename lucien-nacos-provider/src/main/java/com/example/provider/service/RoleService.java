package com.example.provider.service;

import java.util.Set;

/**
 * @ClassName RoleService
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/7 16:42
 * @Version 1.0
 */
public interface RoleService {

    Set<String> getRolesByUserId(Integer id);
}
