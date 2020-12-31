package com.example.provider.config.mongo;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

/**
 * @ClassName MongoDbFileOperate
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/11 16:07
 * @Version 1.0
 */
@Log4j2
@Component
public class MongoDbFileOperate {

    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private GridFsTemplate gridFsTemplate;
    @Resource
    private GridFSBucket gridFSBucket;

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public FileDocument saveFile(MultipartFile file) throws IOException {
        String md5 = SecureUtil.md5(file.getInputStream());
        FileDocument fileDocument = new FileDocument();
        fileDocument.setMd5(md5);
        return saveFile(fileDocument, file);
    }

    private FileDocument saveFile(FileDocument fileDocument, MultipartFile file) throws IOException {
        Assert.notNull(fileDocument, "fileDocument is null");
        Assert.notNull(file, "file is null");
        Assert.notNull(fileDocument.getMd5(), "file is null");
        //已存在该文件，则实现秒传
        Optional<FileDocument> oldFileDocumentOp = getByMd5(fileDocument.getMd5(), false);

        if (oldFileDocumentOp.isPresent()) {
            FileDocument oldFileDocument = oldFileDocumentOp.get();
            fileDocument.setGridFsId(oldFileDocument.getGridFsId());
        } else {
            String gridFsId = uploadFileToGridFS(file.getInputStream(), file.getContentType());
            fileDocument.setGridFsId(gridFsId);
        }

        if (StrUtil.isNotBlank(file.getOriginalFilename())) {
            fileDocument.setName(file.getOriginalFilename());
        }
        fileDocument.setSize(file.getSize());
        fileDocument.setContentType(file.getContentType());
        fileDocument.setUploadDate(new Date());
        if (StrUtil.isNotBlank(file.getOriginalFilename()) && file.getOriginalFilename().contains(".")) {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            fileDocument.setSuffix(suffix);
        }
        fileDocument.setContent(null);

        fileDocument = mongoTemplate.save(fileDocument, "avatar");

        return fileDocument;
    }

    /**
     * 上传文件到Mongodb的GridFs中
     *
     * @param in
     * @param contentType
     * @return
     */
    private String uploadFileToGridFS(InputStream in, String contentType) {
        String gridfsId = IdUtil.simpleUUID();
        //文件，存储在GridFS中
        gridFsTemplate.store(in, gridfsId, contentType);
        return gridfsId;
    }

    /**
     * 通过md5获取文件
     *
     * @param md5
     * @param getFile 是否获取文件内容
     * @return
     */
    private Optional<FileDocument> getByMd5(String md5, boolean getFile) {
        Query query = new Query().addCriteria(Criteria.where("md5").is(md5));
        FileDocument fileDocument = mongoTemplate.findOne(query, FileDocument.class, "avatar");
        if (fileDocument != null) {
            if (getFile) {
                Query gridQuery = new Query().addCriteria(Criteria.where("filename").is(fileDocument.getGridFsId()));
                try {
                    GridFSFile fsFile = gridFsTemplate.findOne(gridQuery);
                    if (ObjectUtil.isNotEmpty(fsFile)) {
                        GridFSDownloadStream in = gridFSBucket.openDownloadStream(fsFile.getObjectId());
                        if (in.getGridFSFile().getLength() > 0) {
                            GridFsResource resource = new GridFsResource(fsFile, in);
                            fileDocument.setContent(IoUtil.readBytes(resource.getInputStream()));
                            return Optional.of(fileDocument);
                        } else {
                            fileDocument = null;
                            return Optional.empty();
                        }
                    }
                } catch (IOException ex) {
                    log.error("mongodb fileDocument md5 =" + md5, ex);
                    return Optional.empty();
                }
            }
        } else {
            return Optional.empty();
        }
        return Optional.of(fileDocument);
    }

    /**
     * 根据id主键查询文件信息
     *
     * @param id
     * @return
     */
    public Optional<FileDocument> getById(String id) {
        FileDocument fileDocument = mongoTemplate.findById(id, FileDocument.class, "avatar");
        if (fileDocument != null) {
            Query gridQuery = new Query().addCriteria(Criteria.where("filename").is(fileDocument.getGridFsId()));
            try {
                GridFSFile fsFile = gridFsTemplate.findOne(gridQuery);
                if (ObjectUtil.isNotEmpty(fsFile)) {
                    GridFSDownloadStream in = gridFSBucket.openDownloadStream(fsFile.getObjectId());
                    if (in.getGridFSFile().getLength() > 0) {
                        GridFsResource resource = new GridFsResource(fsFile, in);
                        fileDocument.setContent(IoUtil.readBytes(resource.getInputStream()));
                        return Optional.of(fileDocument);
                    } else {
                        fileDocument = null;
                        return Optional.empty();
                    }
                }
            } catch (IOException ex) {
                log.error("mongodb fileDocument id =" + id, ex);
            }
        }
        return Optional.empty();
    }
}