package blossom.project.towelove.framework.oss.strategy;

import blossom.project.towelove.common.enums.OssType;
import blossom.project.towelove.framework.oss.config.AliyunOssProperties;
import blossom.project.towelove.framework.oss.config.OSSProperties;
import com.aliyun.oss.OSS;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

//@AutoConfiguration
public class AliyunOssStrategy implements OssServiceStrategy {

    @Resource
    private OSS oss;

    @Resource
    private AliyunOssProperties properties;

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
    public String getOssPathPrefix() {
        return null;
    }

    @Override
    public String removeFiles(String url) {
        return null;
    }

    @Override
    public OssType getOssType() {
        return OssType.ALIYUN;
    }
}
