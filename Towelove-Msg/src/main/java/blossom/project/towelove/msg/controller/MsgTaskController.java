package blossom.project.towelove.msg.controller;


import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.msg.entity.MsgTask;
import blossom.project.towelove.msg.service.MsgTaskService;



import org.springframework.web.bind.annotation.*;
import java.util.List;

import blossom.project.towelove.msg.entity.MsgTask;
import blossom.project.towelove.msg.dto.MsgTaskRespDTO;
import blossom.project.towelove.msg.req.MsgTaskCreateRequest;
import blossom.project.towelove.msg.req.MsgTaskPageRequest;
import blossom.project.towelove.msg.req.MsgTaskUpdateRequest;
import blossom.project.towelove.msg.service.MsgTaskService;


import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.Executors;



/**
 * (MsgTask) 表控制层[不建议修改，如果有新增的方法，写在子类中]
 *
 * @author 张锦标
 * @since 2023-11-21 19:33:06
 */

@LoveLog
@RestController
@RequestMapping("/v1/msgTask")
@RequiredArgsConstructor
public class MsgTaskController {
  

    MsgTaskService msgTaskService;   
  
   /**
     * 按照ID查询
     * @param msgTaskId
     * @return
     */
    @GetMapping("")
    public Result<MsgTaskRespDTO> getMsgTaskById(@Validated @RequestParam(name = "msgTaskId") @NotNull(message = "msgTaskId Can not be null") Long msgTaskId) {
        MsgTaskRespDTO result = msgTaskService.getMsgTaskById(msgTaskId);
        return Result.ok(result);
    }

    /**
     * 带条件分页查询
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    public Result<PageResponse<MsgTaskRespDTO>> pageQueryMsgTask(@Validated MsgTaskPageRequest requestParam) {
        return Result.ok(msgTaskService.pageQueryMsgTask(requestParam));
    }

    /**
     * 基于ID修改
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<MsgTaskRespDTO> updateMsgTask(@Validated @RequestBody MsgTaskUpdateRequest updateRequest){
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
    public Result<MsgTaskRespDTO> createMsgTask(@RequestBody @Valid MsgTaskCreateRequest createRequest){
        return Result.ok(msgTaskService.createMsgTask(createRequest));

    }
  
}


