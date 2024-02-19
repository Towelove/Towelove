package blossom.project.towelove.loves.controller;

import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.entity.TimeLine;
import blossom.project.towelove.loves.service.TimeLineService;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * (TimeLine) 表控制层
 */
@LoveLog
@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
@Deprecated
public class TimeLineController {

    private final TimeLineService timeLineService;

    /**
     * 创建时间线事件
     *
     * @param files          上传的文件
     * @param timeLine       时间线事件信息
     * @return Result<TimeLine>
     */
    @PostMapping("")
    public Result<TimeLine> createTimeLine(@RequestPart("files") List<MultipartFile> files,
                                           @RequestPart("timeline") @Valid TimeLine timeLine) {
        return Result.ok(timeLineService.createTimeLine(files, timeLine));
    }

    /**
     * 按照ID查询时间线事件
     *
     * @param timeLineId 时间线事件ID
     * @return Result<TimeLine>
     */
    @GetMapping("/{id}")
    public Result<TimeLine> getTimeLineById(@PathVariable("id") @NotNull(message = "TimeLine ID cannot be null") Long timeLineId) {
        TimeLine result = timeLineService.getTimeLineById(timeLineId);
        return Result.ok(result);
    }


    /**
     * 基于ID修改时间线事件
     *
     * @param timeLineId  时间线事件ID
     * @param timeLine 时间线事件信息
     * @return Result<TimeLine>
     */
    @PutMapping("/{id}")
    public Result<TimeLine> updateTimeLine(@PathVariable("id") @NotNull(message = "TimeLine ID cannot be null") Long timeLineId,
                                           @RequestBody @Valid TimeLine timeLine) {
        return Result.ok(timeLineService.updateTimeLine(timeLineId, timeLine));
    }

    /**
     * 基于ID删除时间线事件
     *
     * @param timeLineId 时间线事件ID
     * @return Result<Boolean>
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteTimeLine(@PathVariable("id") @NotNull(message = "TimeLine ID cannot be null") Long timeLineId) {
        return Result.ok(timeLineService.deleteTimeLine(timeLineId));
    }

//    /**
//     * 根据ID批量删除时间线事件
//     *
//     * @param ids ID列表
//     * @return Result<Boolean>
//     */
//    @DeleteMapping("/batch")
//    public Result<Boolean> batchDeleteTimeLine(@RequestBody @Valid List<Long> ids) {
//        return Result.ok(timeLineService.batchDeleteTimeLine(ids));
//    }
}
