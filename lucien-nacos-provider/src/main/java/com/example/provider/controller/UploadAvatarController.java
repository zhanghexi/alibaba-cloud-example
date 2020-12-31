package com.example.provider.controller;

import cn.hutool.http.HttpStatus;
import com.example.consumer.feign.enums.ResultCode;
import com.example.consumer.feign.http.AjaxResult;
import com.example.provider.config.mongo.FileDocument;
import com.example.provider.config.mongo.MongoDbFileOperate;
import com.example.provider.constants.CommonConstants;
import com.example.provider.data.mapper.AuthUserMapper;
import com.example.provider.utils.SecurityUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;

/**
 * @ClassName UploadAvatarController
 * @User zhang
 * @Description 上传用户头像
 * @Author Lucien
 * @Date 2020/12/11 15:23
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/dev-api")
public class UploadAvatarController {

    @Resource
    private MongoDbFileOperate mongoDbFileOperate;

    @Resource
    private AuthUserMapper authUserMapper;

    @PostMapping(value = "/user/uploadAvatar")
    @ResponseBody
    public AjaxResult uploadAvatar(@RequestParam(value = "avatarfile") MultipartFile file) {
        if (file.isEmpty()) {
            return AjaxResult.error(HttpStatus.HTTP_INTERNAL_ERROR, "请选择图片");
        }
        try {
            FileDocument fileDocument = mongoDbFileOperate.saveFile(file);
            if (fileDocument == null) {
                return AjaxResult.error(ResultCode.UPLOAD_FAIL.getCode(), ResultCode.UPLOAD_FAIL.getMsg());
            }
            String url = CommonConstants.AVATAR_PREFIX + fileDocument.getId();
            //修改用户地址
            String username = SecurityUtil.getLoginUser().getUsername();
            authUserMapper.updateUserAvatar(username, url);
            return AjaxResult.success();
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.sysError();
        }
    }

    /**
     * 首页加载用户头像
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/user/avatar/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] image(@PathVariable String id) {
        Optional<FileDocument> fileDocument = mongoDbFileOperate.getById(id);
        return fileDocument.orElse(null).getContent();
    }
}
