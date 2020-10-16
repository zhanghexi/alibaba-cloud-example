package com.example.provider.config.server;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName ServerConfig
 * @User zhang
 * @Description 获取项目的ip和端口号
 * @Author Lucien
 * @Date 2020/10/16 10:41
 * @Version 1.0
 */
@Log4j2
@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    public String getTotalUrl() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            String totalUrl = "http://" + address.getHostAddress() + ":" + this.serverPort;
            return totalUrl;
        } catch (UnknownHostException e) {
            log.error("获取请求地址异常:{}", e.getMessage());
        }
        return "";
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }
}