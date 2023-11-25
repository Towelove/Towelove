package blossom.project.towelove.framework.oss.service;

import blossom.project.towelove.framework.oss.strategy.FileUploadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
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
public class FileUploadService implements FileUploadStrategy {

    @Autowired
    private FileUploadStrategy fileUploadStrategy;

    /**
     * 单文件上传
     * @param file  文件
     * @return
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        return fileUploadStrategy.uploadFile(file);
    }

    /**
     * 多文件上传
     * @param files 文件
     * @param type 0：cf 1：countdownlatch
     * @return
     * @throws Exception
     */
    @Override
    public List<String> uploadFiles(List<MultipartFile> files, Integer type) throws Exception {
        return fileUploadStrategy.uploadFiles(files,type);
    }

    @Override
    public String getOssPathPrefix(Integer type) {
        return fileUploadStrategy.getOssPathPrefix(type);
    }
}
