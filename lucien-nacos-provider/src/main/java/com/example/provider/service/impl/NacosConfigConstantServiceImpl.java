package com.example.provider.service.impl;

import com.example.provider.service.NacosConfigConstantService;
import org.springframework.stereotype.Service;

/**
 * @ClassName NacosConfigConstantServiceImpl
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/10/16 11:03
 * @Version 1.0
 */
@Service
public class NacosConfigConstantServiceImpl implements NacosConfigConstantService {

    @Override
    public String getNacosConstant(String msg) {
        return msg;
    }
}