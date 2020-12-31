package com.example.provider.data.mapper;

import com.example.consumer.feign.model.LoginParamDTO;
import com.example.provider.data.model.AuthUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AuthUserMapper extends Mapper<AuthUser> {

    /**
     * 根据登录参数查询是否存在用户信息
     *
     * @param loginParamDTO
     * @return
     */
    AuthUser queryUserInfo(@Param("param") LoginParamDTO loginParamDTO);


    /**
     * 查询单个用户信息
     *
     * @param username
     * @return
     */
    AuthUser findUserByUsername(@Param("username") String username);

    /**
     * 上传(修改)用户头像地址
     *
     * @param username
     * @param url
     * @return
     */
    boolean updateUserAvatar(@Param("username") String username, @Param("avatar") String url);
}