package com.huanletao.shop.controller;

import com.huanletao.pojo.JsonResult;
import com.huanletao.util.FastDFSClient;
import com.huanletao.util.FastDFSConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {


    @RequestMapping("/upload")
    public JsonResult upload(MultipartFile file){
        String name = file.getOriginalFilename();
        int index = name.lastIndexOf(".");
        String ext = name.substring(index + 1);
        try {
            FastDFSClient client = new FastDFSClient("classpath:config/fdfs_client.conf");
            String result = client.upload(file.getBytes(), ext);
            return  new JsonResult(true, FastDFSConfig.FASTDFS_URL+result);
        } catch (Exception e) {
            e.printStackTrace();
            return  new JsonResult(false, e.getMessage());
        }

    }
}
