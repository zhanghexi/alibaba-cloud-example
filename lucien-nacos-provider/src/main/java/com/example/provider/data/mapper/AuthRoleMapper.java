package com.example.provider.data.mapper;

import com.example.provider.data.model.AuthRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AuthRoleMapper extends Mapper<AuthRole> {

    /**
     * 根据用户id查询用户角色
     *
     * @param id
     * @return
     */
    List<AuthRole> findRoleByUserId(@Param("id") Integer id);
}