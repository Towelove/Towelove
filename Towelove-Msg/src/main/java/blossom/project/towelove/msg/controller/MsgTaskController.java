package blossom.project.towelove.msg.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.msg.service.MsgTaskService;



import org.springframework.web.bind.annotation.*;
import java.util.List;

import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import blossom.project.towelove.common.request.msg.MsgTaskCreateRequest;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.request.msg.MsgTaskUpdateRequest;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 *
 * @author 张锦标
 * @since 2023-11-21 19:33:06
 */

@LoveLog
@RestController
@RequestMapping("/v1/msg-task")
@RequiredArgsConstructor
public class MsgTaskController {
  

    MsgTaskService msgTaskService;   
  
   /**
     * 按照ID查询
     * @param msgTaskId
     * @return
     */
    @GetMapping("")
    public Result<MsgTaskResponse> getMsgTaskById(@Validated @RequestParam(name = "msgTaskId") @NotNull(message = "msgTaskId Can not be null") Long msgTaskId) {
        MsgTaskResponse result = msgTaskService.getMsgTaskById(msgTaskId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<MsgTaskResponse>> pageQueryMsgTask(@Validated MsgTaskPageRequest requestParam) {
        return Result.ok(msgTaskService.pageQueryMsgTask(requestParam));
    }

    /**
     * 基于ID修改
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<MsgTaskResponse> updateMsgTask(@Validated @RequestBody MsgTaskUpdateRequest updateRequest){
       return Result.ok(msgTaskService.updateMsgTask(updateRequest));
    }

    /**
     * 基于ID修改
     * @param msgTaskId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteMsgTaskById(@RequestParam @Validated Long msgTaskId){
        return Result.ok(msgTaskService.deleteMsgTaskById(msgTaskId));
    }

    /**
     * 根据ID批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    public Result<Boolean> batchDeleteMsgTask(@RequestBody List<Long> ids){
        return Result.ok(msgTaskService.batchDeleteMsgTask(ids));
    }

    /**
     * 创建
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<MsgTaskResponse> createMsgTask(@RequestBody @Valid MsgTaskCreateRequest createRequest){
        return Result.ok(msgTaskService.createMsgTask(createRequest));

    }
  
}


