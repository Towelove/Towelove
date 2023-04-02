package com.towelove.file.controller;


import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.file.domain.LoveAlbum;
import com.towelove.file.domain.LoveLogs;
import com.towelove.file.domain.vo.LoveAlbumPageReqVO;
import com.towelove.file.service.LoveAlbumService;
import com.towelove.system.api.model.MailAccountBaseVO;
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
@RequestMapping("/loveAlbum")
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
    public R<PageResult<LoveAlbum>> list(@RequestBody LoveAlbumPageReqVO pageReqVO) {

        PageResult<LoveAlbum> loveAlbumPageResult =
                loveAlbumService.selectPage(pageReqVO);
        return R.ok(loveAlbumPageResult);
    }

    /**
     * 根据ID查询恋爱相册详情
     * @param loveAlbumId 恋爱相册ID
     * @return 结果
     */
    @GetMapping(value = "/getInfo/{loveAlbumId}")
    public R<LoveAlbum> getInfo(@PathVariable Long loveAlbumId) {
        LoveAlbum loveAlbum = loveAlbumService.selectLoveAlbumById(loveAlbumId);
        return R.ok(loveAlbum);
    }

    /**
     * 新增恋爱相册
     * @param loveAlbum 恋爱相册
     * @return 结果
     */
    @PostMapping("/add")
    public R<Long> add(@RequestBody LoveAlbum loveAlbum) {
        return R.ok(loveAlbumService.insertLoveAlbum(loveAlbum));
    }

    /**
     * 修改恋爱相册
     *
     * @param loveAlbum 恋爱相册
     * @return 结果
     */
    @PostMapping("/edit")
    public R<Boolean> edit(@RequestBody LoveAlbum loveAlbum) {
        return R.ok(loveAlbumService.updateLoveAlbum(loveAlbum));
    }

    /**
     * 删除恋爱相册
     * @param loveAlbumIds 恋爱相册
     * @return 结果
     */
    @GetMapping("/remove/{loveAlbumIds}")
    public R<Boolean> remove(@PathVariable ArrayList<Long> loveAlbumIds) {
        return R.ok(loveAlbumService.deleteLoveAlbum(loveAlbumIds));
    }
}

