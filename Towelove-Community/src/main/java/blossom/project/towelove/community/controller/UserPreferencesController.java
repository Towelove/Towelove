package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.entity.UserPreferences;
import blossom.project.towelove.community.service.UserPreferencesService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.entity.UserPreferences;
import blossom.project.towelove.community.dto.UserPreferencesRespDTO;
import blossom.project.towelove.community.req.UserPreferencesCreateRequest;
import blossom.project.towelove.community.req.UserPreferencesPageRequest;
import blossom.project.towelove.community.req.UserPreferencesUpdateRequest;
import blossom.project.towelove.community.service.UserPreferencesService;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.Executors;



/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 00:08:01
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description: 
 */

@LoveLog
@RestController
@RequestMapping("/v1/userPreferences")
@RequiredArgsConstructor
public class UserPreferencesController {
  

    UserPreferencesService userPreferencesService;   
  
   /**
     * 按照ID查询
     * @param userPreferencesId
     * @return
     */
    @GetMapping("")
    public Result<UserPreferencesRespDTO> getUserPreferencesById(@Validated @RequestParam(name = "userPreferencesId") @NotNull(message = "userPreferencesId Can not be null") Long userPreferencesId) {
        UserPreferencesRespDTO result = userPreferencesService.getUserPreferencesById(userPreferencesId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<UserPreferencesRespDTO>> pageQueryUserPreferences(@Validated UserPreferencesPageRequest requestParam) {
        return Result.ok(userPreferencesService.pageQueryUserPreferences(requestParam));
    }

    /**
     * 基于ID修改
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<UserPreferencesRespDTO> updateUserPreferences(@Validated @RequestBody UserPreferencesUpdateRequest updateRequest){
       return Result.ok(userPreferencesService.updateUserPreferences(updateRequest));
    }

    /**
     * 基于ID修改
     * @param userPreferencesId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteUserPreferencesById(@RequestParam @Validated Long userPreferencesId){
        return Result.ok(userPreferencesService.deleteUserPreferencesById(userPreferencesId));
    }

    /**
     * 根据ID批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteUserPreferences(@RequestBody List<Long> ids){
        return Result.ok(userPreferencesService.batchDeleteUserPreferences(ids));
    }

    /**
     * 创建
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<UserPreferencesRespDTO> createUserPreferences(@RequestBody @Valid UserPreferencesCreateRequest createRequest){
        return Result.ok(userPreferencesService.createUserPreferences(createRequest));

    }
  
}



