package com.example.provider.config.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName FileDocument
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/11 16:03
 * @Version 1.0
 */
@Data
public class FileDocument implements Serializable {

    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 存储时间
     */
    private String warehousingTime;
    /**
     * 上传时间
     */
    private Date uploadDate;
    /**
     * 文件MD5值
     */
    private String md5;
    /**
     * 文件内容
     */
    private byte[] content;
    /**
     * 文件类型
     */
    private String contentType;
    /**
     * 文件后缀名
     */
    private String suffix;
    /**
     * 大文件管理GridFS的ID
     */
    private String gridFsId;
}