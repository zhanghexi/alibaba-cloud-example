package com.example.provider.config.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * @ClassName BootOAuthExceptionJacksonSerializer
 * @User zhang
 * @Description 定义异常BootOAuth2Exception 的序列化类
 * @Author Lucien
 * @Date 2020/9/22 22:29
 * @Version 1.0
 */
public class BootOauth2ExceptionJacksonSerializer extends StdSerializer<BootOauth2Exception> {

    protected BootOauth2ExceptionJacksonSerializer() {
        super(BootOauth2Exception.class);
    }

    @Override
    @SneakyThrows
    public void serialize(BootOauth2Exception ex, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("code", ex.getErrorCode());
        jsonGenerator.writeStringField("msg", ex.getMessage());
        jsonGenerator.writeEndObject();
    }
}