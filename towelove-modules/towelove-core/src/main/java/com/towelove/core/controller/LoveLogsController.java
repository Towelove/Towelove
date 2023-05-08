package com.towelove.core.controller;


import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.JwtUtils;
import com.towelove.core.domain.lovelogs.*;
import com.towelove.core.service.LoveAlbumService;
import com.towelove.core.service.LoveLogsService;
import com.towelove.core.service.impl.MinioSysFileServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

/**
 * 恋爱日志表(LoveLogs) 控制层
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:07
 */
@RestController
@RequestMapping("/core/loveLogs")
public class LoveLogsController {
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private LoveLogsService loveLogsService;

    @Autowired
    private LoveAlbumService loveAlbumService;

    @Autowired
    private MinioSysFileServiceImpl minioSysFileService;


    /**
     * 根据分页条件和恋爱日志表信息查询恋爱日志表数据
     *
     * @return 分页数据
     */
    @GetMapping("/pageWithTime")
    public R<PageResult<LoveLogs>> page(@RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                        HttpServletRequest request) {
        Long loveAlbumId = getLoveAlbumIdByHeader(request);
        return R.ok(loveLogsService.selectPage(pageNo, pageSize, loveAlbumId.toString()), "分页查询成功");
    }

    /**
     * 根据ID查询恋爱日志表详情
     *
     * @param loveLogsId 恋爱日志表ID
     * @return 结果
     */
    @GetMapping(value = "/get/{loveLogsId}")
    public R<LoveLogs> get(@PathVariable("loveLogsId") Long loveLogsId) {
        LoveLogs loveLogs = loveLogsService.selectLoveLogsById(loveLogsId);
        return R.ok(loveLogs, "获取特定的恋爱日志成功");
    }
    private Long getLoveAlbumIdByHeader(HttpServletRequest request){
        //createReqVO.setLoveAlbumId(createReqVO.getLoveAlbumId());
        String authorization = request.getHeader("Authorization");
        String userId = JwtUtils.getUserId(authorization);
        Long loveAlbumId = loveAlbumService.selectLoveAlbumIdByUserId(userId);
        return loveAlbumId;
    }
    /**
     * 新增恋爱日志表
     *
     * @param files       文件
     * @param createReqVO 恋爱日志请求VO
     * @return
     */
    @PostMapping("/add")
    public R<Long> add(@RequestPart("files") MultipartFile[] files,
                       //@RequestParam("loveLogs") String jsonStr,
                       @RequestPart("loveLogs") LoveLogsCreateReqVO createReqVO,
                       HttpServletRequest request) {

        Long loveAlbumId = getLoveAlbumIdByHeader(request);

        //统计每个文件的url
        String photoUrls = String.join(",",
                minioSysFileService.uploadloadFileMultiple(files));
        createReqVO.setUrls(photoUrls);
        createReqVO.setLoveAlbumId(loveAlbumId);
        return R.ok(loveLogsService.insertLoveLogs(createReqVO),"创建恋爱日志成功");
    }

    /**
     * 修改恋爱日志表
     *
     * @param updateReqVO 恋爱日志表
     * @return 结果
     */
    @PutMapping("/edit")
    public R<Boolean> edit(@RequestPart(value = "files",required = false) MultipartFile[] files,
                           //@RequestParam("secHandGoods") String jsonStr,
                           HttpServletRequest request,
                           @RequestPart("loveLogs") LoveLogsUpdateReqVO updateReqVO) throws Exception {
        //LoveLogs loveLogs = JSON.parseObject(jsonStr, LoveLogs.class);
        Long loveAlbumId = getLoveAlbumIdByHeader(request);
        minioSysFileService.deleteFiles(updateReqVO.getUrls());
        //统计每个文件的url
        String photoUrls = String.join(",", minioSysFileService.uploadloadFileMultiple(files));
        updateReqVO.setUrls(photoUrls);
        updateReqVO.setLoveAlbumId(loveAlbumId);
        boolean isUpdate = loveLogsService.updateLoveLogs(updateReqVO);
        return R.ok(isUpdate);
    }

    /**
     * 删除恋爱日志表
     *
     * @param loveLogsId 恋爱日志表
     * @return 结果
     */
    @DeleteMapping("/remove/{loveLogsId}")
    public R<Boolean> remove(@PathVariable("loveLogsId") Long loveLogsId) {
        LoveLogs loveLogs = loveLogsService.selectLoveLogsById(loveLogsId);
        try {
            minioSysFileService.deleteFiles(loveLogs.getUrls());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return R.ok(loveLogsService.deleteLoveLog(loveLogsId));
    }
}

