package com.example.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName ProviderApplication
 * @User zhang
 * @Description @EnableDiscoveryClient注解表明是一个Nacos客户端，该注解是 SpringCloud 提供的原生注解
 * @Author Lucien
 * @Date 2020/8/27 14:33
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients //打开Feign客服端
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
