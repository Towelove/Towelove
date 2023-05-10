package com.towelove.monitor.ai.service;

import ai.djl.modality.Classifications;
import com.towelove.monitor.ai.util.GarbageUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/5/10 9:28
 * GarbageService类
 */
@Service
public class GarbageService {
    public static void main(String[] args) {
        GarbageUtils herbUtil = new GarbageUtils();
        String path = "D:\\desktop\\photos\\ylg1.jpg";
        try {
            File file = new File(path);
            InputStream inputStream = new FileInputStream(file);
            herbUtil.predict(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
