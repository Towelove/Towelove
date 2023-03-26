package com.towelove.file.controller;


import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.file.domain.LoveLogs;
import com.towelove.file.domain.vo.LoveLogsPageReqVO;
import com.towelove.file.service.LoveLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 恋爱日志表(LoveLogs) 控制层
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:07
 */
@RestController
@RequestMapping("/loveLogs")
public class LoveLogsController {

    @Autowired
    private LoveLogsService loveLogsService;

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
    public R<Long> add(@RequestBody LoveLogs loveLogs) {
        return R.ok(loveLogsService.insertLoveLogs(loveLogs));
    }

    /**
     * 修改恋爱日志表
     *
     * @param loveLogs 恋爱日志表
     * @return 结果
     */
    @PutMapping("/edit")
    public R<Boolean> edit(@RequestBody LoveLogs loveLogs) {
        return R.ok(loveLogsService.updateLoveLogs(loveLogs));
    }

    /**
     * 删除恋爱日志表
     *
     * @param loveLogsIds 恋爱日志表
     * @return 结果
     */
    @DeleteMapping("/remove/{loveLogsIds}")
    public R<Boolean> remove(@PathVariable ArrayList<Long> loveLogsIds) {
        return R.ok(loveLogsService.deleteLoveLogs(loveLogsIds));
    }
}

