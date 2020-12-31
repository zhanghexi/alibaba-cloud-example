package com.example.provider.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.consumer.feign.enums.ResultCode;
import com.example.consumer.feign.http.AjaxResult;
import com.example.consumer.feign.model.LoginParamDTO;
import com.example.provider.constants.CommonConstants;
import com.example.provider.service.ImgCodeService;
import com.example.provider.utils.RedisUtil;
import com.google.code.kaptcha.Producer;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * @ClassName ImgCodeServiceImpl
 * @User zhang
 * @Description
 * @Author Lucien
 * @Date 2020/11/30 2:17
 * @Version 1.0
 */
@Log4j2
@Service
public class ImgCodeServiceImpl implements ImgCodeService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private Producer producerMath;

    /**
     * 生成验证码
     *
     * @return
     */
    @SneakyThrows
    @Override
    public AjaxResult getImgCodeByFeignApi() {
        AjaxResult imageCodeResult = new AjaxResult();
        String capStr = null, code = null;
        BufferedImage image = null;

        /*UUID*/
        String uuId = IdUtil.fastUUID();
        /*存入redis的key值*/
        String verifyKey = CommonConstants.CODE_KEY + uuId;
        /*预生成验证码(计算过程)*/
        String capText = producerMath.createText();
        capStr = capText.substring(0, capText.lastIndexOf("@"));
        code = capText.substring(capText.lastIndexOf("@") + 1);
        image = producerMath.createImage(capStr);

        /*存入redis*/
        redisUtil.set(verifyKey, code, CommonConstants.EXPIRATION_TIME);
        // 转换流信息写出
        FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", outputStream);

            imageCodeResult.put("uuid", uuId);
            imageCodeResult.put("img", Base64.encode(outputStream.toByteArray()));
            return AjaxResult.success(imageCodeResult);
        } catch (Exception e) {
            log.error("生成验证码异常:{}", e.getMessage());
            return AjaxResult.error(ResultCode.IMAGE_CODE_ERROR.getCode(), ResultCode.IMAGE_CODE_ERROR.getMsg());
        } finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            if (image != null) {
                image.flush();
            }
        }
    }

    /**
     * 校验验证码
     *
     * @param loginParamDTO
     * @return
     */
    @Override
    public AjaxResult validateImgCode(LoginParamDTO loginParamDTO) {
        log.info("校验验证码入参:{}", JSONUtil.toJsonStr(loginParamDTO));
        /*校验码未输入*/
        if (StrUtil.isEmpty(loginParamDTO.getValidateCode())) {
            return AjaxResult.error(ResultCode.IMAGE_CODE_NOT_EMPTY.getCode(), ResultCode.IMAGE_CODE_NOT_EMPTY.getMsg());
        }
        String codeKey = CommonConstants.CODE_KEY + loginParamDTO.getUuid();
        String cacheCode = (String) redisUtil.get(codeKey);
        /*校验码过期*/
        if (cacheCode == null || cacheCode.equals("")) {
            return AjaxResult.error(ResultCode.IMAGE_CODE_EXPIRED.getCode(), ResultCode.IMAGE_CODE_EXPIRED.getMsg());
        }
        /*校验码输错*/
        if (!cacheCode.equalsIgnoreCase(loginParamDTO.getValidateCode())) {
            return AjaxResult.error(ResultCode.IMAGE_INPUT_ERROR.getCode(), ResultCode.IMAGE_INPUT_ERROR.getMsg());
        }
        /*返回正确*/
        return AjaxResult.success(true);
    }
}