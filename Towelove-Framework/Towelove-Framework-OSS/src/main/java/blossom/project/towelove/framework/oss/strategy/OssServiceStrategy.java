package blossom.project.towelove.framework.oss.strategy;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/25 18:57
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * FileUploadStrategy接口
 */
public interface OssServiceStrategy {

    /**
     * 上传单个文件
     * @param file  文件
     * @return 文件路径
     * @throws Exception
     */
    String uploadFile(MultipartFile file) throws Exception;

    /**
     * 文件异步批量多线程上传 支持选择上传方式
     * @param files 文件
     * @param type 0：cf 1：countdownlatch
     * @return 返回文件名称与路径的集合
     * @throws Exception
     */
    List<String> uploadFiles(List<MultipartFile> files,Integer type) throws Exception;

    /**
     * 返回当前文件的路径前缀
     * @return
     */
    String getOssPathPrefix();


    /**
     * 根据url删除文件，多个文件之间以 ， 分割
     * @param url 要删除文件的url
     * @return
     */
    String removeFiles(String url);


}
