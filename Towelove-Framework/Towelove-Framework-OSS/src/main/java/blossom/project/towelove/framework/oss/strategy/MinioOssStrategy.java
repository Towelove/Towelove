package blossom.project.towelove.framework.oss.strategy;


import blossom.project.towelove.common.exception.ClientException;
import blossom.project.towelove.common.exception.RemoteException;
import blossom.project.towelove.common.exception.ServerException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.utils.file.FileUploadUtil;
import blossom.project.towelove.framework.oss.config.MinioProperties;
import cn.hutool.core.collection.CollectionUtil;
import com.aliyun.oss.ServiceException;
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
import java.util.ArrayList;
import java.util.Arrays;
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
@Slf4j
@AutoConfiguration
public class MinioOssStrategy implements OssServiceStrategy {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    @Qualifier(value = "ioDynamicThreadPool")
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private MinioProperties minioProperties;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        try {
            String fileName = FileUploadUtil.extractFilename(file);
            //判断桶是否存在
            String bucketName = FileUploadUtil.getBucketName(file);
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (found) {
                InputStream is = file.getInputStream();
                PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(fileName)
                        .stream(is, file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build();
                //上传文件
                minioClient.putObject(args);
                //关闭文件io流
                is.close();
                return fileName;
            } else {
                log.error("bucket does not exist");
            }
        } catch (Exception e) {
            log.error("upload file caused a exception", e);
            throw e;
        }
        return "upload file error!";
    }

    /**
     * 使用CompletableFuture进行异步上传
     *
     * @param files
     */
    public List<String> uploadFilesAsyncWithCompletableFuture(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();
        if (CollectionUtil.isEmpty(files)) {
            log.info("上传的文件集合为空...");
            return fileNames;
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
        //这里由于使用的是串行stream流，所以创建的CF集合是按照files的顺序而创建的
        //因此可以确保他是有序的
        List<CompletableFuture<String>> futures =
                files.stream().map(file ->
                        uploadFileAsync(file, bucketName)
                                .exceptionally(e -> {
                                    System.err.println(e.getMessage()); // 异常处理
                                    //file.getName();
                                    return null;
                                    //return e.fillInStackTrace().getMessage();
                                })).collect(Collectors.toList());

        // 使用 Future 的返回顺序
        fileNames = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        return fileNames;
    }

    /**
     * 政治执行异步文件上传的方法
     *
     * @param file       文件
     * @param bucketName 文件所属桶名称
     * @return
     */
    private CompletableFuture<String> uploadFileAsync(MultipartFile file, String bucketName) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String fileName = FileUploadUtil.extractFilename(file);
                log.info("当前上传的文件名称是：{}", fileName);
                InputStream is = file.getInputStream();
                PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(is,
                        file.getSize(), -1).contentType(file.getContentType()).build();
                minioClient.putObject(args); // 假设minioClient.putObject执行了上传操作
                is.close();
                return fileName;
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
            } finally {
            }
        }, threadPoolExecutor);
    }


    /**
     * 异步上传，使用CountDownLatch
     * 适合需要等待所有文件都上传完毕才返回的场景
     *
     * @param files 文件集合
     * @return 文件路径
     */
    public List<String> uploadFilesAsyncWithCountDownLatch(List<MultipartFile> files) {
        List<String> fileNames = new ArrayList<>();
        if (CollectionUtil.isEmpty(files)) {
            log.info("上传的文件为空...");
            return fileNames;
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
                    log.info("当前上传的文件名称是：{}", fileName);
                    InputStream is = file.getInputStream();
                    PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(is,
                            file.getSize(), -1).contentType(file.getContentType()).build();
                    minioClient.putObject(args); // 假设minioClient.putObject执行了上传操作
                    fileNames.add(fileName);
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

        return fileNames;
    }

    /**
     * 批量文件上传
     *
     * @param files 文件
     * @param type  0：cf 1：countdownlatch
     * @return 返回的文件所在oss服务中的路径
     */
    @Override
    public List<String> uploadFiles(List<MultipartFile> files, Integer type) {
        List<String> fileNames = new ArrayList<>();
        switch (type) {
            case 0: {
                fileNames = uploadFilesAsyncWithCompletableFuture(files);
                break;
            }
            default: {
                fileNames = uploadFilesAsyncWithCountDownLatch(files);
            }
        }
        return fileNames;
    }


    @Override
    public String getOssPathPrefix() {
        return minioProperties.getUrl();
    }


    /**
     * 根据文件在minio中的位置返回文件
     *
     * @param name 文件目录名称 /xxx/xxx/xxx.jpg
     * @return 文件流
     * @throws Exception
     */
    public GetObjectResponse getFile(String name) throws Exception {
        String bucketName = FileUploadUtil.getBucketNameByFileExtension(name);
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(name)
                .build();
        return minioClient.getObject(getObjectArgs);
    }

    /**
     * 使用 CompletableFuture 异步删除 MinIO 中的文件。
     *
     * @param photoUrls 用英文逗号分隔的文件 URL 列表
     * @return 删除的文件名称列表
     */
    public String removeFiles(String photoUrls) {
        String[] names = photoUrls.split(",");
        String bucketName = FileUploadUtil.getBucketNameByFileExtension(names[0]);

        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                throw new ServerException("Minio中并没有这个桶:" + bucketName, BaseErrorCode.BUCKET_NOT_FOUND);
            }

            // 并行删除所有文件
            CompletableFuture<?>[] deleteFutures = Arrays.stream(names)
                    .map(name -> CompletableFuture.runAsync(() -> {
                        try {
                            RemoveObjectArgs removeObjectsArgs =
                                    RemoveObjectArgs.builder().bucket(bucketName).object(name).build();
                            minioClient.removeObject(removeObjectsArgs);
                        } catch (Exception e) {
                            throw new RuntimeException("Error deleting file: " + name, e);
                        }
                    }))
                    .toArray(CompletableFuture[]::new);

            // 等待所有删除操作完成
            CompletableFuture.allOf(deleteFutures).join();

            return Arrays.toString(names);
        } catch (Exception e) {
            throw new ServiceException("Error during file deletion", e);
        }
    }
}
