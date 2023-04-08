package com.towelove.core.controller;


import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.file.FileUtils;
import com.towelove.core.domain.SysFile;
import com.towelove.core.domain.lovelogs.LoveLogs;
import com.towelove.core.service.impl.MinioSysFileServiceImpl;
import io.minio.GetObjectResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 文件请求处理
 *
 * @author 张锦标
 */
@RequestMapping("/core/file")
@RestController
public class SysFileController {
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);
    //文件上传上线
    public static final Integer FILE_UPLOAD_LIMIT_NUM = 3;
    @Autowired
    private MinioSysFileServiceImpl minioSysFileService;
    @Autowired
    @Qualifier("fileThreadPool")
    private ThreadPoolExecutor fileThreadPool;

    @Autowired
    private static final Semaphore SEMAPHORE = new Semaphore(FILE_UPLOAD_LIMIT_NUM);
    @Autowired
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(FILE_UPLOAD_LIMIT_NUM);

    /**
     * 实现多文件多线程上传
     *
     * @param files 要上传的文件
     * @return 返回恋爱日志信息
     */
    @ApiOperation(value = "实现多文件多线程上传", notes = "实现多文件多线程上传")
    @ResponseBody
    @PostMapping("/upload")
    public R<LoveLogs> upload(@RequestParam("files") MultipartFile[] files,
                              @RequestParam("description") String description) {
        LoveLogs loveLogs = new LoveLogs();
        loveLogs.setDescription(description);
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(files.length);
        for (int i = 0; i < files.length; i++) {
            try {
                MultipartFile file = files[i];
                //TODO 使用CountDownLaunch或者ComplatableFuture或者Semaphore
                //来完成多线程的文件上传
                fileThreadPool.submit(() -> {
                    try {
                        String url = minioSysFileService.uploadFile(file);
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
        //统计每个文件的url
        String photoUrls = String.join(",", list);
        loveLogs.setUrls(photoUrls);
        //返回结果
        return R.ok(loveLogs);
    }


    /**
     * 文件下载
     *
     * @param name     文件名称
     * @param response 响应流
     */
    @GetMapping("/download")
    public void downloadMinio(@RequestParam String name, HttpServletResponse response) {
        try (ServletOutputStream os = response.getOutputStream()) {
            response.setContentType("image/jpeg");
            GetObjectResponse file = minioSysFileService.getFile(name);
            int len = 0;
            byte[] buffre = new byte[1024 * 10];
            while ((len = file.read(buffre)) != -1) {
                os.write(buffre, 0, len);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单文件上传
     *
     * @param file 要上传的文件
     * @return 返回文件名称以及url
     */
    @PostMapping("/uploadMinio")
    public R<SysFile> uploadMinio(@RequestParam("file") MultipartFile file) {
        try {
            // 上传并返回访问地址
            String url = minioSysFileService.uploadFile(file);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }
}
