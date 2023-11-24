package blossom.project.towelove.framework.oss.service;


import blossom.project.towelove.common.utils.file.FileUploadUtil;
import blossom.project.towelove.framework.oss.config.MinioConfig;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Minio 文件存储服务
 *
 * @author 张锦标
 */
@AutoConfiguration
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */

    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = FileUploadUtil.extractFilename(file);
        //判断桶是否存在
        String bucketName = FileUploadUtil.getBucketName(file);
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

        if (found) {
            InputStream is = file.getInputStream();
            PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName) //设定桶名称
                    .object(fileName) //要下载的文件
                    .stream(is, file.getSize(), -1) //文件上传流
                    .contentType(file.getContentType()) //设定文件类型
                    .build();
            //上传文件
            minioClient.putObject(args);
            is.close();
            //return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + fileName;
            return fileName;
        } else {
            System.out.println("bucket does not exist");
            throw new RuntimeException("my-bucketname " + bucketName + " does not exist");
        }
    }

    /**
     * 根据文件在minio中的位置返回文件
     *
     * @param name 文件目录
     * @return 文件流
     * @throws Exception
     */
    public GetObjectResponse getFile(String name) throws Exception {
        String bucketName = FileUploadUtil.getBucketNameByFileExtension(name);
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucketName).object(name).build();
        return minioClient.getObject(getObjectArgs);
    }

    /**
     * 根据图片的url删除图片,并且以英文逗号作为分隔符
     *
     * @param photoUrls 二手商品对象的url
     */
    public void deleteFiles(String photoUrls) throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {
        //这里默认urls是同类型的文件，也就是要么都是图片，要么都是视频
        String[] names = photoUrls.split(",");
        String bucketName = FileUploadUtil.getBucketNameByFileExtension(names[0]);

        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (found) {
            for (String name : names) {
                try {
                    RemoveObjectArgs removeObjectsArgs =
                            RemoveObjectArgs.builder().bucket(bucketName).object(name).build();
                    minioClient.removeObject(removeObjectsArgs);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            throw new RuntimeException("Minio中并没有这个桶:" + bucketName);
        }
    }
}
