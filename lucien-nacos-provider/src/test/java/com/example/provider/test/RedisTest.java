package com.example.provider.test;

import cn.hutool.json.JSONUtil;
import com.example.provider.constants.CommonConstants;
import com.example.provider.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @ClassName RedisTest
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/12/10 16:47
 * @Version 1.0
 */
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void getRedisKeysTest() {
        Set<String> keysWithPrefix = redisUtil.getKeysWithPrefix(CommonConstants.AUTH_PREFIX);
        log.info(JSONUtil.toJsonStr(keysWithPrefix) + "\nauth前缀的个数：" + keysWithPrefix.size());
    }
}
