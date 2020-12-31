package com.example.provider.data.mapper;

import com.example.provider.data.model.Operation;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OperationMapper extends Mapper<Operation> {

    int addOperationLog(@Param("param") Operation operation);
}