package com.example.provider.config.security.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * @ClassName RedisTokenStoreConfig
 * @User zhang
 * @Description token存储redis的配置类
 * @Author Lucien
 * @Date 2020/12/1 19:39
 * @Version 1.0
 */
@Configuration
public class RedisTokenStoreConfig {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "redisTokenStore")
    public TokenStore redisTokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        //设置redis token存储中的前缀
        redisTokenStore.setPrefix("auth_to_access:");
        return redisTokenStore;
    }
}