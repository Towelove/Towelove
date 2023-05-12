package com.towelove.core.controller;


import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.JwtUtils;
import com.towelove.core.domain.lovelist.LoveList;
import com.towelove.core.service.LoveAlbumService;
import com.towelove.core.service.LoveListService;
import com.towelove.core.service.impl.MinioSysFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 代办列表(LoveList) 控制层
 *
 * @author 张锦标
 * @since 2023-05-12 19:33:55
 */
@RestController
@RequestMapping("/core/loveList")
public class LoveListController {

    @Autowired
    private LoveListService loveListService;

    @Autowired
    private LoveAlbumService loveAlbumService;
    @Autowired
    private MinioSysFileServiceImpl minioSysFileService;
    private Long getLoveAlbumIdByHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String userId = JwtUtils.getUserId(token);
        Long loveAlbumId = loveAlbumService.selectLoveAlbumIdByUserId(userId);
        return loveAlbumId;
    }
    /**
     * 全查询
     * @return 数据对象
     */
    @GetMapping("/list")
    public R<List<LoveList>> list(HttpServletRequest request) {
        Long loveAlbumId = getLoveAlbumIdByHeader(request);
        List<LoveList> loveLists = loveListService.
                selectLoveListByLoveAlbumId(loveAlbumId);
        return R.ok(loveLists);
    }


    /**
     * 根据ID查询代办列表详情
     * @param loveListId 代办列表ID
     * @return 结果
     */
    @GetMapping(value = "/get/{id}")
    public R<LoveList> getInfo(@PathVariable("id") Long loveListId) {
        LoveList loveList = loveListService.selectLoveListById(loveListId);
        return R.ok(loveList);
    }

    /**
     * 新增代办列表
     * @param loveList 代办列表
     * @return 返回添加成功后的id
     */
    @PostMapping("/add")
    public R<Long> add(@RequestBody LoveList loveList,
                       HttpServletRequest request) {
        Long loveAlbumId = getLoveAlbumIdByHeader(request);
        loveList.setLoveAlbumId(loveAlbumId);
        return R.ok(loveListService.insertLoveList(loveList));
    }

    /**
     * 修改代办列表
     * @param loveList 代办列表
     * @return 返回是否修改成功
     */
    @PutMapping("/edit")
    public R<Boolean> edit(@RequestPart("loveList") LoveList loveList,
                           @RequestPart(value = "photo",required = false)
                           MultipartFile photo,
                           HttpServletRequest request) throws Exception {
        Long loveAlbumId = getLoveAlbumIdByHeader(request);
        String path = minioSysFileService.uploadFile(photo);
        loveList.setPhoto(path);
        loveList.setLoveAlbumId(loveAlbumId);
        return R.ok(loveListService.updateLoveList(loveList));
    }

    /**
     * 删除代办列表
     * @param loveListId 代办列表
     * @return 返回是否删除成功
     */
    @DeleteMapping("/remove/{loveListId}")
    public R<Boolean> remove(@PathVariable("loveListId") Long loveListId) {
        return R.ok(loveListService.deleteLoveList(loveListId));
    }
}

