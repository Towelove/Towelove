package blossom.project.towelove.loves.controller;

import blossom.project.towelove.common.request.loves.diary.DiaryCreateRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.love.diary.LoveDiaryDTO;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.entity.LoveDiary;
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
     * @param request
     * @return
     */
    @PostMapping("")
    public Result<LoveDiaryDTO> createDiary(@Validated @RequestBody DiaryCreateRequest request){
        return Result.ok(diariesService.createDiary(request));
   }

    /**
     * 日记同步开关
     * @param id
     * @param synchronous
     * @return
     */
   @PostMapping("/sync")
   public Result<Boolean> fetchSynchronous(@Validated @NotNull(message = "id could not be null") @RequestParam("id") Long id
           ,@RequestParam("synchronous") Boolean synchronous){
        return Result.ok(diariesService.fetchSynchronous(id,synchronous));
   }
}
