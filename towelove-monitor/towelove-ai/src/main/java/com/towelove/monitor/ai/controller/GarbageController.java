package com.towelove.monitor.ai.controller;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import com.towelove.monitor.ai.util.GarbageUtils;
import com.towelove.monitor.ai.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: 张锦标
 * @date: 2023/5/10 9:27
 * GarbageController类
 */
@RestController
@RequestMapping("/garbage")
public class GarbageController {


    @PostMapping("/sorting")
    public String garbageSorting(@RequestPart("file") MultipartFile file) throws IOException, ModelNotFoundException,
            MalformedModelException {
        InputStream is = file.getInputStream();
        String s = ImageUtils.garbageSorting(is);
        is.close();
        return s;
    }
}
