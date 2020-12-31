package com.example.provider.config.mongo;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;

import javax.annotation.Resource;

/**
 * @ClassName MongoDBConfiguration
 * @User zhang
 * @Description mongoDB总配置类
 * @Author Lucien
 * @Date 2020/12/11 16:06
 * @Version 1.0
 */
@Configuration
public class MongoDBConfiguration {

    @Resource
    private MongoDbFactory mongoDbFactory;

    @Bean
    public GridFSBucket getGridFSBuckets() {
        MongoDatabase db = mongoDbFactory.getDb();
        return GridFSBuckets.create(db);
    }
}