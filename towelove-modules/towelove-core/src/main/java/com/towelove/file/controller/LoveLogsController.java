package com.towelove.file.controller;


import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.file.domain.LoveLogs;
import com.towelove.file.domain.vo.LoveLogsPageReqVO;
import com.towelove.file.service.LoveLogsService;
import com.towelove.file.service.impl.MinioSysFileServiceImpl;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 恋爱日志表(LoveLogs) 控制层
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:07
 */
@RestController
@RequestMapping("/loveLogs")
public class LoveLogsController {
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private LoveLogsService loveLogsService;

    @Autowired
    private MinioSysFileServiceImpl minioSysFileService;
    @Autowired
    @Qualifier("fileThreadPool")
    private ThreadPoolExecutor fileThreadPool;
    /**
     * 查询所有数据
     *
     * @return 全查询
     */
    @GetMapping("/list")
    public List<LoveLogs> list() {
        return loveLogsService.selectList();
    }

    /**
     * 根据分页条件和恋爱日志表信息查询恋爱日志表数据
     * @param pageReqVO 分页查询条件
     * @return 分页数据
     */
    @GetMapping("/page")
    public R<PageResult<LoveLogs>> page(LoveLogsPageReqVO pageReqVO){
        return R.ok(loveLogsService.selectPage(pageReqVO));
    }
    /**
     * 根据ID查询恋爱日志表详情
     * @param loveLogsId 恋爱日志表ID
     * @return 结果
     */
    @GetMapping(value = "/getInfo/{loveLogsId}")
    public R<LoveLogs> getInfo(@PathVariable Long loveLogsId) {
        LoveLogs loveLogs = loveLogsService.selectLoveLogsById(loveLogsId);
        return R.ok(loveLogs);
    }

    /**
     * 新增恋爱日志表
     *
     * @param loveLogs 恋爱日志表
     * @return 结果
     */
    @PostMapping("/add")
    public R<Long> add( @RequestPart("files") MultipartFile[] files,
                        //@RequestParam("loveLogs") String jsonStr,
                        @RequestPart("loveLogs") LoveLogs loveLogs,
                        @RequestParam("loveAlbumId")Long loveAlbumId) {
        loveLogs.setLoveAlbumId(loveAlbumId);
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
        return R.ok(loveLogsService.insertLoveLogs(loveLogs));
    }

    /**
     * 修改恋爱日志表
     *
     * @param loveLogs 恋爱日志表
     * @return 结果
     */
    @PutMapping("/edit")
    public R<Boolean> edit(  @RequestPart("files") MultipartFile[] files,
                             //@RequestParam("secHandGoods") String jsonStr,
                             @RequestPart("loveLogs") LoveLogs loveLogs) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //LoveLogs loveLogs = JSON.parseObject(jsonStr, LoveLogs.class);
        minioSysFileService.deleteFiles(loveLogs.getUrls());
        //线程安全
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
        boolean isUpdate = loveLogsService.updateLoveLogs(loveLogs);
        return R.ok(isUpdate);
    }

    /**
     * 删除恋爱日志表
     *
     * @param loveLogsIds 恋爱日志表
     * @return 结果
     */
    @DeleteMapping("/remove/{loveLogsIds}")
    public R<Boolean> remove(@PathVariable ArrayList<Long> loveLogsIds) {
        for (Long loveLogsId : loveLogsIds) {
            LoveLogs loveLogs = loveLogsService.selectLoveLogsById(loveLogsId);
            try {
                minioSysFileService.deleteFiles(loveLogs.getUrls());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return R.ok(loveLogsService.deleteLoveLogs(loveLogsIds));
    }
}

