package blossom.project.towelove.framework.oss.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@AutoConfiguration
public class OssStrategyFactory implements BeanPostProcessor {
    //private final TencentOssStrategy tencentOssStrategy;
    //
    //private final AliyunOssStrategy aliyunOssStrategy;

    private final MinioOssStrategy minioOssStrategy;

    public final Map<Integer,FileUploadStrategy> map = new HashMap();

    /**
     * 多OSS文件上传选择
     * @param file 文件
     * @param type 0：minio 1：aliyun 2：tencentyun
     * @return 返回文件的上传路径
     */
    public String uploadFile(MultipartFile file, Integer type){
        try {
            return map.getOrDefault(type,map.get(0)).uploadFile(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件集合
     * @param files 要上传的文件集合
     * @param type 0：使用cf多线程上传 1：使用countdownlatch多文件上传
     * @return 文件所在oss服务中的路径
     */
    public List<String> uploadFiles(List<MultipartFile> files,Integer type){
        try {
            return map.getOrDefault(type,map.get(0)).uploadFiles(files,type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化策略
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        map.put(0,minioOssStrategy);
        //map.put(1,aliyunOssStrategy);
        //map.put(2,tencentOssStrategy);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
