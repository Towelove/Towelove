package blossom.project.towelove.framework.oss.service;

import blossom.project.towelove.framework.oss.strategy.OssStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/25 19:14
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * FileUploadService类
 * 当前方法用于提供最后的多OSS服务之间进行文件上传的方法
 * 后续可以考虑增加故障的自动切换功能，暂时默认先选择使用
 * 默认策略模式以及手动的方式来切换数据源
 */
@AutoConfiguration
@RequiredArgsConstructor
public class OssService {

    private final OssStrategyFactory ossStrategyFactory;


    /**
     * 单文件上传 默认上传到MinIO中
     * 可以在Nacos中配置type来修改OSS服务类型
     * @param file  文件
     * @param ossType oss服务类型
     * @return
     * @throws Exception
     */
    public String uploadFile(MultipartFile file,@Nullable Integer ossType){
        return ossStrategyFactory.uploadFile(file,ossType);
    }





    /**
     * 多文件异步上传 默认上传到MinIO中
     * 可以在Nacos中配置type来修改OSS服务类型
     * @param files 文件
     * @param ossType oss服务类型
     * @param mulType 0：cf 1：countdownlatch
     * @return
     * @throws Exception
     */
    public List<String> uploadFilesAsync(List<MultipartFile> files, @Nullable Integer ossType, Integer mulType)  {
        return ossStrategyFactory.uploadFilesAsync(files,ossType,mulType);
    }


    /**
     * 获取oss服务前缀
     * @param ossType
     * @return
     */
    public String getOssPathPrefix(@Nullable Integer ossType) {
        return ossStrategyFactory.getOssPathPrefix(ossType);
    }

    /**
     * 从oss中按照文件URL得到要删除的文件名称
     * @param removedUrl 要删除的文件URL-》得到文件路径
     * @param ossType oss服务类型
     */
    public String removeFiles(String removedUrl, Integer ossType) {
        return ossStrategyFactory.removeFiles(removedUrl,ossType);
    }
}
