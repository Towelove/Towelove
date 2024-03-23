package blossom.project.towelove.loves.controller;

import blossom.project.towelove.common.request.loves.diary.DiaryCreateRequest;
import blossom.project.towelove.common.request.loves.diary.QuickWriterDiaryRequest;
import blossom.project.towelove.common.request.loves.diary.UpdateDiaryRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.love.diary.LoveDiaryDTO;
import blossom.project.towelove.common.response.love.diary.LoveDiaryVO;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.service.DiariesService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.controller
 * @className: DiaryController
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 17:31
 * @version: 1.0]
 * 日记相关
 */
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
@LoveLog
public class DiaryController {

    private final DiariesService diariesService;

    /**
     * 创建日记
     *
     * @param request
     * @return
     */
    @PostMapping("")
    public Result<LoveDiaryDTO> createDiary(@Validated @RequestBody DiaryCreateRequest request) {
        return Result.ok(diariesService.createDiary(request));
    }

    /**
     * 日记同步开关
     *
     * @param id
     * @param synchronous
     * @return
     */
    @PostMapping("/sync")
    public Result<Boolean> fetchSynchronous(@Validated @NotNull(message = "id could not be null") @RequestParam("id") Long id
            , @RequestParam("synchronous") Boolean synchronous) {
        return Result.ok(diariesService.fetchSynchronous(id, synchronous));
    }

    /**
     * 根据日记Id获取日记详情
     *
     * @param id
     * @return
     */
    @GetMapping("")
    public Result<LoveDiaryVO> getLoveDiaryById(@Validated @NotNull(message = "id could not be null")
                                                @RequestParam(required = true, name = "id") Long id) {
        return Result.ok(diariesService.getLoveDiaryById(id));
    }

    /**
     * 小记一下
     *
     * @param request
     * @return
     */
    @PostMapping("/quick")
    public Result<String> quickWriteDiary(@RequestBody @Validated QuickWriterDiaryRequest request) {
        return Result.ok(diariesService.quickWrite(request));
    }

    /**
     * 更新日记
     *
     * @param updateDiaryRequest
     * @return
     */
    @PostMapping("/update")
    public Result<String> updateDiaryById(@Validated @RequestBody UpdateDiaryRequest updateDiaryRequest) {
        return Result.ok(diariesService.updateDiary(updateDiaryRequest));
    }

    /**
     * 删除日记
     * @param id
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleted(@Validated @NotNull(message = "id could not be null")
                                   @RequestParam(required = true, name = "id") Long id) {
        return Result.ok(diariesService.deleteById(id));
    }
}
