package blossom.project.towelove.loves.controller;

import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.loves.diary.DiaryCollectionCreateRequest;
import blossom.project.towelove.common.request.loves.diary.DiaryCollectionPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.love.diary.DiaryCollectionDTO;
import blossom.project.towelove.common.response.love.diary.DiaryTitleDTO;
import blossom.project.towelove.common.response.love.diary.LoveDiaryDTO;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.convert.DiaryCollectionConvert;
import blossom.project.towelove.loves.entity.LoveDiaryCollection;
import blossom.project.towelove.loves.service.DiariesService;
import lombok.RequiredArgsConstructor;
import org.simpleframework.xml.Path;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.loves.controller
 * @className: DiariesController
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/2/20 15:22
 * @version: 1.0
 * 日记册相关
 */
@RestController
@RequestMapping("/diaries")
@RequiredArgsConstructor
@LoveLog
public class DiaryCollectionController {

    private final DiariesService diariesService;

    /**
     * 得到全部日记册信息
     * @param coupleId
     * @return
     */
    @GetMapping("")
    public Result<List<DiaryCollectionDTO>> getDiaryById(@Validated @RequestParam("coupleId")
                                                               @NotNull(message = "coupleId could not be null") Long coupleId){
        return Result.ok(diariesService.getDiaryCollectionById(coupleId));
    }

    /**
     * 分页得到日记册信息
     * @param request
     * @return
     */

    @GetMapping("/page")
    public Result<PageResponse<DiaryCollectionDTO>> getDiaryCollectionByPage(@Validated DiaryCollectionPageRequest request){
        return Result.ok(diariesService.getDiaryCollectionByPage(request));
    }

    @PostMapping("")
    public Result<DiaryCollectionDTO> createDiaryCollection(@Validated @RequestBody DiaryCollectionCreateRequest request){
        return Result.ok(diariesService.createDiaryCollection(request));
    }

    @DeleteMapping("")
    public Result<Boolean> deletedDiaryCollectionById(@Validated @RequestParam("id")
                                                      @NotNull(message = "id could not be null") Long id){
        return Result.ok(diariesService.deleteById(id));
    }

    @GetMapping("/{id}")
    public Result<List<DiaryTitleDTO>> getLoveDiaryByCollectionId(@PathVariable("id") Long collectionId){
        return Result.ok(diariesService.getLoveDirayByCollectionId(collectionId));
    }


}
