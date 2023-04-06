package com.towelove.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传接口
 * 
 * @author 张锦标
 */
public interface ISysFileService
{
    /**
     * 文件上传接口
     * 
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    public String uploadFile(MultipartFile file) throws Exception;
    public default void downloadFile(String name) throws Exception{

    }
    public default List<String> uploadloadFileMultiple(MultipartFile[] files){
        return null;
    }
}
