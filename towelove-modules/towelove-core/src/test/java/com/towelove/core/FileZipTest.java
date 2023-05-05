//package com.towelove.core;
//
//import com.towelove.common.core.utils.file.FileUploadUtils;
//import com.towelove.core.config.MinioConfig;
//import io.minio.BucketExistsArgs;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import net.coobird.thumbnailator.Thumbnails;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//
///**
// * @author: 张锦标
// * @date: 2023/4/12 19:10
// * FileZipTest类
// */
//@SpringBootTest
//public class FileZipTest {
//    @Autowired
//    private MinioClient minioClient;
//    @Autowired
//    private MinioConfig minioConfig;
//
//    public static final String PREFIX_PATH = "D:/desktophoto/file/";
//
//    public static void main(String[] args) {
//        System.out.println(PREFIX_PATH+"/2023/04/08/");
//        String path = PREFIX_PATH+"2023/04/08/mysql_20230408145210A006.jpg";
//        String dir = path.substring(0, path.lastIndexOf("/"));
//        File file = new File(dir);
//        System.out.println(file.mkdirs());
//        System.out.println(file.isFile());
//        System.out.println(file.isDirectory());
//    }
//
//    @Test
//    public String uploadFile(MultipartFile file) throws Exception {
//        String fileName = FileUploadUtils.extractFilename(file);
//        //判断桶是否存在
//        boolean found =
//                minioClient.bucketExists(BucketExistsArgs.builder()
//                        .bucket(minioConfig.getBucketName()).build());
//        if (found) {
//            InputStream is = file.getInputStream();
//            //为当前文件创建目录
//            String path = PREFIX_PATH+fileName;
//            String dir = path.substring(0, path.lastIndexOf("/"));
//            File fileDir = new File(dir);
//            fileDir.mkdirs();
//            //压缩后的文件位置
//            Thumbnails.of(is)
//                    .size(160,160)
//                    .toFile(path);
//            FileInputStream fis = new FileInputStream(path);
//            System.out.println("my-bucketname exists");
//            PutObjectArgs args = PutObjectArgs.builder()
//                    .bucket(minioConfig.getBucketName()) //设定桶名称
//                    .object(fileName) //要下载的文件
//                    .stream(fis, file.getSize(), -1) //文件上传流
//                    .contentType(file.getContentType()) //设定文件类型
//                    .build();
//            //上传文件
//            minioClient.putObject(args);
//            is.close();
//            fis.close();
//            //return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + fileName;
//            return fileName;
//        } else {
//            System.out.println("my-bucketname does not exist");
//            throw new RuntimeException("my-bucketname " + minioConfig.getBucketName()
//                    + " does not exist");
//        }
//    }
//
//}
