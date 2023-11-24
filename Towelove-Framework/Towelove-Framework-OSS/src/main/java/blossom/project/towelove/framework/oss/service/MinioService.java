package blossom.project.towelove.framework.oss.service;


import blossom.project.towelove.common.utils.file.FileUploadUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * Minio 文件存储服务
 *
 * @author 张锦标
 */
@AutoConfiguration
@Slf4j
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    @Qualifier(value = "ioDynamicThreadPool")
    private ThreadPoolExecutor threadPoolExecutor;


    /**
     * 使用CompletableFuture进行异步上传
     * @param files
     */
    public void uploadFilesAsyncWithCompletableFuture(List<MultipartFile> files) {
        if (CollectionUtil.isEmpty(files)){
            log.info("上传的文件集合为空...");
            return;
        }
        String bucketName = FileUploadUtil.getBucketName(files.get(0));
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                System.out.println("bucket does not exist");
                throw new RuntimeException("bucketname " + bucketName + " does not exist");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<CompletableFuture<Void>> futures = files.stream()
                .map(file -> uploadFileAsync(file, bucketName)
                        .exceptionally(e -> {
                            System.err.println(e.getMessage()); // 异常处理
                            return null;
                        }))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    /**
     * minio上传操作
     * @param file
     * @param bucketName
     * @return
     */
    private CompletableFuture<Void> uploadFileAsync(MultipartFile file, String bucketName) {
        return CompletableFuture.runAsync(() -> {
            try {
                String fileName = FileUploadUtil.extractFilename(file);
                //判断桶是否存在
                log.info("当前上传的文件名称是：{}",fileName);
                InputStream is = file.getInputStream();
                PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName) //设定桶名称
                        .object(fileName) //要下载的文件
                        .stream(is, file.getSize(), -1) //文件上传流
                        .contentType(file.getContentType()) //设定文件类型
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
            }
        }, threadPoolExecutor);
    }
    /**
     * 异步上传，使用CountDownLatch
     * 适合需要等待所有文件都上传完毕才返回的场景
     * @param files
     * @return
     */
    public Boolean uploadFilesWithCountDownLatch(List<MultipartFile> files) {
        if (CollectionUtil.isEmpty(files)) {
            log.info("上传的文件为空...");
            return true;
        }
        String bucketName = FileUploadUtil.getBucketName(files.get(0));
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                System.out.println("bucket does not exist");
                throw new RuntimeException("bucketname " + bucketName + " does not exist");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CountDownLatch latch = new CountDownLatch(files.size());
        for (MultipartFile file : files) {
            threadPoolExecutor.execute(() -> {
                try {
                    String fileName = FileUploadUtil.extractFilename(file);
                    //判断桶是否存在
                    log.info("当前上传的文件名称是：{}",fileName);
                    InputStream is = file.getInputStream();
                    PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName) //设定桶名称
                            .object(fileName) //要下载的文件
                            .stream(is, file.getSize(), -1) //文件上传流
                            .contentType(file.getContentType()) //设定文件类型
                            .build();
                } catch (Exception e) {
                    e.printStackTrace(); // 这里处理异常
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();  // 等待所有上传任务完成
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

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
                    //设定桶名称
            PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName)
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
            throw new RuntimeException("bucketname " + bucketName + " does not exist");
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
