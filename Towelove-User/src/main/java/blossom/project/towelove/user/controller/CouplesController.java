package blossom.project.towelove.user.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.user.CouplesCreateRequest;
import blossom.project.towelove.common.request.user.CouplesUpdateRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.user.CouplesRespDTO;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.user.service.CouplesService;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;



/**
 * @author: ZhangBlossom
 * @date: 2024-01-17 13:36:03
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */

@LoveLog
@RestController
@RequestMapping("/v1/couples")
@RequiredArgsConstructor
public class CouplesController {
  

    private final CouplesService couplesService;
  
   /**
     * 按照ID查询
     * @param couplesId
     * @return
     */
    @GetMapping("")
    public Result<CouplesRespDTO> getCouplesById(@Validated @RequestParam(name = "couplesId") @NotNull(message = "couplesId Can not be null") Long couplesId) {
        CouplesRespDTO result = couplesService.getCouplesById(couplesId);
        return Result.ok(result);
    }


    /**
     * 基于ID修改
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<CouplesRespDTO> updateCouples(@Validated @RequestBody CouplesUpdateRequest updateRequest){
       return Result.ok(couplesService.updateCouples(updateRequest));
    }

    /**
     * 基于ID修改
     * @param couplesId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteCouplesById(@RequestParam @Validated Long couplesId){
        return Result.ok(couplesService.deleteCouplesById(couplesId));
    }

    /**
     * 根据ID批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteCouples(@RequestBody List<Long> ids){
        return Result.ok(couplesService.batchDeleteCouples(ids));
    }

    /**
     * 创建
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<CouplesRespDTO> createCouples(@RequestBody @Valid CouplesCreateRequest createRequest){
        return Result.ok(couplesService.createCouples(createRequest));

    }
  
}



