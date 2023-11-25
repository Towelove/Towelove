package blossom.project.towelove.framework.oss.strategy;

import blossom.project.towelove.framework.oss.config.OSSProperties;
import blossom.project.towelove.framework.oss.config.TencentOssProperties;
import com.qcloud.cos.COSClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@AutoConfiguration
public class TencentOssStrategy implements FileUploadStrategy {
    @Resource
    private COSClient cosClient;

    @Resource
    private TencentOssProperties properties;

    @Resource
    private OSSProperties ossProperties;


    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        return null;
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> files, Integer type) throws Exception {
        return null;
    }

    @Override
    public String getOssPathPrefix(Integer type) {
        return null;
    }
}
