package blossom.project.towelove.framework.oss.strategy;


import blossom.project.towelove.common.enums.OssType;
import blossom.project.towelove.common.exception.ServerException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.utils.file.FileUploadUtil;
import blossom.project.towelove.framework.oss.config.MinioProperties;
import cn.hutool.core.collection.CollectionUtil;
import com.aliyun.oss.ServiceException;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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

    /**
     * 代码中，异步任务主要是进行文件的读取和上传
     * 这属于 I/O 密集型的任务，所以使用虚拟线程池是合适的
     */
    @Autowired
    @Qualifier(value = "virtualThreadThreadPool")
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
                return minioProperties.getUrl() + "/" + bucketName + "/" + fileName;
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
                                    return null;
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
                return minioProperties.getUrl() + "/" + bucketName + "/" + fileName;
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
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
                    fileNames.add(minioProperties.getUrl() + "/" + bucketName + "/" + fileName);
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

    @Override
    public OssType getOssType() {
        return OssType.MINIO;
    }


    /**
     * 从minio中删除文件
     * @param photoUrls 要删除文件的url
     * @return
     */

    public String removeFiles(String photoUrls) {
        // 拆分 URL
        String[] urls = photoUrls.split(",");
        String bucketName = FileUploadUtil.getBucketNameByFileExtension(urls[0]);

        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                throw new ServerException("Minio中并没有这个桶:" + bucketName, BaseErrorCode.BUCKET_NOT_FOUND);
            }

            // 并行删除所有文件
            CompletableFuture<?>[] deleteFutures = Arrays.stream(urls)
                    .map(this::extractObjectNameFromUrl) // 从 URL 提取对象名称
                    .map(objectName -> CompletableFuture.runAsync(() -> {
                        try {
                            RemoveObjectArgs removeObjectsArgs =
                                    RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build();
                            minioClient.removeObject(removeObjectsArgs);
                        } catch (Exception e) {
                            throw new RuntimeException("Error deleting file: " + objectName, e);
                        }
                    }))
                    .toArray(CompletableFuture[]::new);

            // 等待所有删除操作完成
            CompletableFuture.allOf(deleteFutures).join();

            return Arrays.toString(urls);
        } catch (Exception e) {
            throw new ServiceException("Error during file deletion", e);
        }
    }

    /**
     * 从完整的 URL 中提取 MinIO 中的对象名称
     *
     * @param url 文件的完整 URL
     * @return MinIO 中的对象名称
     */
    private String extractObjectNameFromUrl(String url) {
        // 假设 URL 是 "http://minio.example.com/mybucket/myfolder/myfile.jpg"
        // 您需要从中提取 "myfolder/myfile.jpg"
        // 这里的实现方式取决于您的 URL 格式和 MinIO 配置
        String prefixToRemove = minioProperties.getUrl() + "/" + FileUploadUtil.getBucketNameByFileExtension(url) + "/";
        return url.replace(prefixToRemove, "");
    }
}
