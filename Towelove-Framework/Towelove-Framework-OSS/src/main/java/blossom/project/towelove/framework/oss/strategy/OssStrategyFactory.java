package blossom.project.towelove.framework.oss.strategy;

import blossom.project.towelove.framework.oss.config.OSSProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@AutoConfiguration
public class OssStrategyFactory implements BeanPostProcessor {
    private final TencentOssStrategy tencentOssStrategy;

    private final AliyunOssStrategy aliyunOssStrategy;

    private final MinioOssStrategy minioOssStrategy;

    private final Map<Integer, OssServiceStrategy> map = new HashMap();

    private final OSSProperties ossProperties;
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
        map.put(1,aliyunOssStrategy);
        map.put(2,tencentOssStrategy);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    /**
     * 多OSS文件上传选择
     * @param file 文件
     * @param ossType 0：minio 1：aliyun 2：tencentyun
     * @return 返回文件的上传路径
     */
    public String uploadFile(MultipartFile file, Integer ossType){
        try {
            //使用用户的 如果用户没有传递 那么使用nacos默认的配置
            return map.getOrDefault(ossType,map.get(ossProperties.getType())).uploadFile(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件集合
     * @param files 要上传的文件集合
     * @param ossType oss服务类型 默认minio
     * @param mulType 0：使用cf多线程上传 1：使用countdownlatch多文件上传
     * @return 文件所在oss服务中的路径
     */
    public List<String> uploadFilesAsync(List<MultipartFile> files, Integer ossType, Integer mulType){
        try {
            return map.getOrDefault(ossType,map.get(ossProperties.getType()))
                    .uploadFiles(files,mulType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取oss服务对应的前缀
     * @param ossType oss服务类型
     * @return
     */
    public String getOssPathPrefix(Integer ossType) {
        return map.getOrDefault(ossType,
                map.get(ossProperties.getType())).getOssPathPrefix();
    }

    public String removeFiles(String urls,Integer ossType){
        return map.getOrDefault(ossType,map.get(ossProperties.getType())).removeFiles(urls);
    }

}
