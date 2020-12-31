package com.example.provider.config.redis;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName RedisConfig
 * @User zhang
 * @Description Redis基础配置类
 * @Author Lucien
 * @Date 2020/11/30 2:39
 * @Version 1.0
 */
@Data
@Configuration
@ConditionalOnClass(RedisTemplate.class)
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // 使用StringRedisSerializer实现序列化，防止redisTemplate.keys()无返回结果
        StringRedisSerializer serializer = new StringRedisSerializer(StandardCharsets.UTF_8);

        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        System.out.println("已初始化Redis配置类...");
        return redisTemplate;
    }
}