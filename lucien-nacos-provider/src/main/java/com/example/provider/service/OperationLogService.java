package com.example.provider.service;

import com.example.provider.data.model.Operation;

/**
 * @ClassName OperationLogService
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/10/16 16:30
 * @Version 1.0
 */
public interface OperationLogService {

    /**
     * 增加日志到数据库
     *
     * @param operation
     */
    int addOperationLog(Operation operation);
}
