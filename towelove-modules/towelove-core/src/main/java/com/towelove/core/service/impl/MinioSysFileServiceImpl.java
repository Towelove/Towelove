package com.towelove.core.service.impl;


import com.towelove.common.core.utils.StringUtils;
import com.towelove.common.core.utils.file.FileUploadUtils;
import com.towelove.core.service.ISysFileService;
import com.towelove.core.config.MinioConfig;
import io.minio.*;
import io.minio.errors.*;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.*;

/**
 * Minio 文件存储
 *
 * @author 张锦标
 */
@Service
@Primary
public class MinioSysFileServiceImpl implements ISysFileService {
    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient minioClient;
    @Autowired
    @Qualifier("fileThreadPool")
    private ThreadPoolExecutor fileThreadPool;
    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = FileUploadUtils.extractFilename(file);
        //判断桶是否存在
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(minioConfig.getBucketName()).build());
        if (found) {
            InputStream is = file.getInputStream();
            System.out.println("my-bucketname exists");
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName()) //设定桶名称
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
            System.out.println("my-bucketname does not exist");
            throw new RuntimeException("my-bucketname " + minioConfig.getBucketName()
                    + " does not exist");
        }
    }

    /**
     * 实现多文件多线程上传接口
     * @param files
     * @return
     */
    @Override
    public List<String> uploadloadFileMultiple(MultipartFile[] files) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(files.length);
        for (int i = 0; i < files.length; i++) {
            try {
                MultipartFile file = files[i];
                //TODO 使用CountDownLaunch或者ComplatableFuture或者Semaphore
                //来完成多线程的文件上传
                fileThreadPool.submit(() -> {
                    try {
                        String url = uploadFile(file);
                        list.add(url);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        //表示一个文件已经被完成
                        countDownLatch.countDown();
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            //阻塞直到所有的文件完成复制
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> uploadloadFileMultiples(MultipartFile[] files) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Semaphore semaphore = new Semaphore(files.length);
        for (int i = 0; i < files.length; i++) {
            try {
                semaphore.acquire();
                MultipartFile file = files[i];
                //TODO 使用CountDownLaunch或者ComplatableFuture或者Semaphore
                //来完成多线程的文件上传
                fileThreadPool.submit(() -> {
                    try {
                        String url = uploadFile(file);
                        list.add(url);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        //表示一个文件已经被完成
                        semaphore.release();
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
    public List<String> uploadloadFileMultipled(MultipartFile[] files) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(files.length);
        for (int i = 0; i < files.length; i++) {
            try {
                MultipartFile file = files[i];
                //TODO 使用CountDownLaunch或者ComplatableFuture或者Semaphore
                //来完成多线程的文件上传
                fileThreadPool.submit(() -> {
                    try {
                        String url = uploadFile(file);
                        list.add(url);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        //表示一个文件已经被完成
                        try {
                            //进入await
                            cyclicBarrier.await(10,TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (BrokenBarrierException e) {
                            throw new RuntimeException(e);
                        } catch (TimeoutException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public GetObjectResponse getFile(String name) throws Exception{
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(name)
                .build();
        return  minioClient.getObject(getObjectArgs);
    }
    /**
     * 根据图片的url删除图片
     *
     * @param photoUrls 对象的url
     */
    public void deleteFiles(String photoUrls) throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException {
        if(StringUtils.isEmpty(photoUrls)){
            return ;
        }
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build());
        if (found) {

            String[] names = photoUrls.split(",");
            for (String name : names) {
                try {
                    RemoveObjectArgs removeObjectsArgs =
                            RemoveObjectArgs.builder().bucket(minioConfig.getBucketName()).object(name).build();
                    minioClient.removeObject(removeObjectsArgs);
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        }else{
            throw new RuntimeException("Minio中并没有这个桶:"+minioConfig.getBucketName());
        }
    }
}
