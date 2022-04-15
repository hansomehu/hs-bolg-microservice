package com.hs.blog.controller;


import com.hs.blog.service.ImageService;
import com.hs.blog.utils.ImageUtils;
import com.hs.blog.xo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Resource
    private ImageUtils imageUtils;

    @Value("${IMAGE.BASE.URL}")
    private String baseUrl;

    @PostMapping("/upload/image")
    public Result upLoad(@RequestParam("image") MultipartFile file){
        try{
            String newFileName = imageUtils.genImageName();
            InputStream inputStream = file.getInputStream();
            Result result = imageService.upLoad(newFileName, inputStream);
            if (result.isSuccess()){
                return new Result(true, 200, baseUrl+"/"+newFileName, null);
            }else {
                return new Result(false, 400, result.getMsg(), null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, 400, "IOException", null);
        }

    }
}
