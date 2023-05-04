package com.towelove.core.controller;


import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.utils.OfficialMailUtil;
import com.towelove.common.security.annotation.RequiresPermissions;
import com.towelove.core.domain.lovealbum.*;
import com.towelove.core.service.LoveAlbumService;
import org.apache.ibatis.annotations.Delete;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 恋爱相册(LoveAlbum) 控制层
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:05
 */
@RestController
@RequestMapping("/core/loveAlbum")
public class LoveAlbumController {

    @Autowired
    private LoveAlbumService loveAlbumService;

    /**
     * 查询所有数据
     *
     * @return 全查询
     */
    @GetMapping("/list")
    public List<LoveAlbum> list() {
        return loveAlbumService.selectList();
    }
    /**
     * 根据分页条件和恋爱相册信息查询恋爱相册数据
     * @param pageReqVO 恋爱相册分页查询条件
     * @return 分页数据对象 (TableDataInfo)
     */
    @GetMapping("/page")
    public R<PageResult<LoveAlbumBaseVO>> list(@RequestBody LoveAlbumPageReqVO pageReqVO) {
        PageResult<LoveAlbumBaseVO> loveAlbumPageResult =
                loveAlbumService.selectPage(pageReqVO);
        return R.ok(loveAlbumPageResult);
    }

    /**
     * 根据ID查询恋爱相册详情
     * @param loveAlbumId 恋爱相册ID
     * @return 结果
     */
    @GetMapping(value = "/get/{loveAlbumId}")
    public R<LoveAlbum> get(@PathVariable("loveAlbumId") Long loveAlbumId) {
        LoveAlbum loveAlbum = loveAlbumService.selectLoveAlbumById(loveAlbumId);
        return R.ok(loveAlbum);
    }

    /**
     * 新增恋爱相册
     * @param createReqVO 恋爱相册
     * @return 结果
     */
    @PostMapping("/add")
    public R<Long> add(@RequestBody LoveAlbumCreateReqVO createReqVO) {
        return R.ok(loveAlbumService.insertLoveAlbum(createReqVO));
    }
    @Autowired
    private OfficialMailUtil officialMailUtil;
    //TODO 这里写一个通过邀请码然后同意邀请来创建恋爱相册的信息
    //TODO 之后恋爱相册的默认信息先写上去 让用户自己修改
    //TODO 只有用户同意了恋爱相册的邀请之后才会真的创建恋爱相册


    /**
     * 修改恋爱相册
     *
     * @param updateReqVO 恋爱相册
     * @return 结果
     */
    @PostMapping("/edit")
    public R<Boolean> edit(@RequestBody LoveAlbumUpdateReqVO updateReqVO) {
        return R.ok(loveAlbumService.updateLoveAlbum(updateReqVO));
    }

    /**
     * 删除恋爱相册
     * @param loveAlbumIds 恋爱相册
     * @return 结果
     */
    @DeleteMapping("/remove/{loveAlbumIds}")
    public R<Boolean> remove(@PathVariable ArrayList<Long> loveAlbumIds) {
        return R.ok(loveAlbumService.deleteLoveAlbum(loveAlbumIds));
    }
}

