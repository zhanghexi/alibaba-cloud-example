package com.example.provider.service.impl;

import com.example.provider.data.mapper.OperationMapper;
import com.example.provider.data.model.Operation;
import com.example.provider.service.OperationLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName OperationLogServiceImpl
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/10/16 16:30
 * @Version 1.0
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Resource
    private OperationMapper operationMapper;

    @Override
    public int addOperationLog(Operation operation) {
        return operationMapper.addOperationLog(operation);
    }
}
