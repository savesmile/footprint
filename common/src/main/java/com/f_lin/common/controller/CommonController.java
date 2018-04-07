package com.f_lin.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.f_lin.common.ImageUploadSetting;
import com.f_lin.common.po.AuthCode;
import com.f_lin.gateway.po.JsonResult;
import com.f_lin.gateway.support.UserId;
import com.f_lin.user_api.po.User;
import com.f_lin.utils.Base64Utils;
import com.f_lin.utils.MapBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @author F_lin
 * @since 2018/4/6
 **/
@CrossOrigin
@RestController
@RequestMapping("/api/common")
public class CommonController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    MongoOperations mongoOperations;
    @Autowired
    ImageUploadSetting imageUploadSetting;


    @PostMapping("/uploadByFile")
    public Object upload(@UserId String userId,
                         @RequestParam("type") String type,
                         @RequestParam("image") MultipartFile multipartFile) {
        if (StringUtils.isEmpty(userId)){
            return JsonResult.error("请登录后操作");
        }
        if (multipartFile.isEmpty() || StringUtils.isBlank(multipartFile.getOriginalFilename())) {
            return JsonResult.error("文件为空");
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            return JsonResult.error("文件类型为空");
        }
        String root_fileName = multipartFile.getOriginalFilename();
        logger.info("上传图片:name={},type={}", root_fileName, contentType);
        Map<String, String> setting = imageUploadSetting.getSetting();
        String filePath = setting.get(type);
        String sPath = imageUploadSetting.getStaticPath();
        String uuid = UUID.randomUUID().toString();

        try {
            uploadFile(multipartFile.getBytes(), filePath, uuid + root_fileName);
        } catch (Exception e) {
            return JsonResult.error("文件上传出错 请稍后重试");
        }
        return JsonResult.success(MapBuilder.of("imagePath", sPath + uuid + root_fileName));
    }

    private static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }


    @PostMapping("/uploadByBase64")
    public Object uploadByBase64(//@UserId String userId,
                                 @RequestBody JSONObject jsonObject) {
       /* if (com.f_lin.utils.StringUtils.isEmpty(userId)) {
            return JsonResult.error("请登录后操作");
        }*/
        Map<String, String> setting = imageUploadSetting.getSetting();
        String path;
        try {
            path = generateImage(jsonObject.getString("image"), setting.get(jsonObject.getString("type")), jsonObject.getString("type"));
        } catch (IOException e) {
            return JsonResult.error("图片上传失败!");
        }
        return JsonResult.success(MapBuilder.of("path", path));
    }

    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     */
    private String generateImage(String imgStr, String path, String type) throws IOException {
        String imgType = "";
        if (imgStr.contains(",")) {
            imgType = imgStr.split(",")[0];
            imgType = imgType.substring(imgStr.indexOf("/") + 1, imgStr.indexOf(";"));
            imgStr = imgStr.split(",")[1];
        }
        if (imgStr == null) return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            File targetFile = new File(path);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            String lPath = UUID.randomUUID().toString() + "." + ("".equals(imgType) ? "" : imgType);
            String oPath = path + lPath;
            OutputStream out = new FileOutputStream(oPath);
            String oP = imageUploadSetting.getStaticPath()
                    + type
                    + File.separator
                    + lPath;
            out.write(b);
            out.flush();
            out.close();
            return oP;
        } catch (Exception e) {
            throw e;
        }
    }
}
