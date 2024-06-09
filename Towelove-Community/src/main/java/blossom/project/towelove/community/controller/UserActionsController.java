package blossom.project.towelove.community.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.community.entity.UserActions;
import blossom.project.towelove.community.service.UserActionsService;


import blossom.project.towelove.framework.log.annotation.LoveLog;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.community.entity.UserActions;
import blossom.project.towelove.community.dto.UserActionsRespDTO;
import blossom.project.towelove.community.req.UserActionsCreateRequest;
import blossom.project.towelove.community.req.UserActionsPageRequest;
import blossom.project.towelove.community.req.UserActionsUpdateRequest;
import blossom.project.towelove.community.service.UserActionsService;


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
@RequestMapping("/user/actions")
@RequiredArgsConstructor
public class UserActionsController {
  

    UserActionsService userActionsService;   
  
   /**
     * 按照ID查询
     * @param userActionsId
     * @return
     */
    @GetMapping("")
    public Result<UserActionsRespDTO> getUserActionsById(@Validated @RequestParam(name = "userActionsId") @NotNull(message = "userActionsId Can not be null") Long userActionsId) {
        UserActionsRespDTO result = userActionsService.getUserActionsById(userActionsId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<UserActionsRespDTO>> pageQueryUserActions(@Validated UserActionsPageRequest requestParam) {
        return Result.ok(userActionsService.pageQueryUserActions(requestParam));
    }

    /**
     * 基于ID修改
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<UserActionsRespDTO> updateUserActions(@Validated @RequestBody UserActionsUpdateRequest updateRequest){
       return Result.ok(userActionsService.updateUserActions(updateRequest));
    }

    /**
     * 基于ID修改
     * @param userActionsId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteUserActionsById(@RequestParam @Validated Long userActionsId){
        return Result.ok(userActionsService.deleteUserActionsById(userActionsId));
    }

    /**
     * 根据ID批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteUserActions(@RequestBody List<Long> ids){
        return Result.ok(userActionsService.batchDeleteUserActions(ids));
    }

    /**
     * 创建
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<UserActionsRespDTO> createUserActions(@RequestBody @Valid UserActionsCreateRequest createRequest){
        return Result.ok(userActionsService.createUserActions(createRequest));

    }
  
}



