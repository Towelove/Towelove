package com.towelove.msg.task.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.towelove.common.core.domain.PageResult;
import com.towelove.common.core.domain.R;
import com.towelove.common.core.web.domain.AjaxResult;
import com.towelove.msg.task.convert.MsgTaskConvert;
import com.towelove.msg.task.domain.MsgTask;
import com.towelove.msg.task.domain.vo.*;
import com.towelove.msg.task.service.MsgTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: 张锦标
 * @date: 2023/3/12 9:37
 * MsgTaskController类
 */
@RestController
@RequestMapping("/msg/task")
public class MsgTaskController {
    @Autowired
    private MsgTaskService msgTaskService;

    /**
     * 创建消息任务
     * @param createReqVO 前端传来的消息任务信息
     * @return 创建成功返回id
     */
    @PostMapping("/create")
    @Operation(summary = "创建消息任务")
    public R<Long> createMsgTask(@Valid @RequestBody MsgTaskCreateReqVO createReqVO){
        return R.ok(msgTaskService.createMsgTask(createReqVO));
    }

    /**
     * 修改消息任务
     * @param updateReqVO 要修改的消息任务消息
     * @return boolean值 返回是否修改成功
     */
    @PutMapping("/update")
    @Operation(summary = "修改消息任务")
    public R<Boolean> updateMailAccount(@Valid @RequestBody MsgTaskUpdateReqVO updateReqVO) {
        return R.ok( msgTaskService.updateMsgTask(updateReqVO));
    }

    /**
     * 删除消息任务
     * @param id 要删除的任务id
     * @return 返回修改是否成功
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除消息任务")
    @Parameter(name = "id", description = "编号", required = true)
    public R<Boolean> deleteMailAccount(@RequestParam Long id) {
        return R.ok(msgTaskService.deleteMsgTask(id));
    }

    /**
     * 获得消息任务根据id
     * @param id 消息任务id
     * @return 返回消息任务消息
     */
    @GetMapping("/get")
    @Operation(summary = "获得消息任务")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public R<MsgTaskRespVO> getMailAccount(@RequestParam("id") Long id) {
        MsgTask msgTask = msgTaskService.getMsgTask(id);
        return R.ok(MsgTaskConvert.INSTANCE.convert(msgTask));
    }

    /**
     * 根据查询条件进行分页
     * @param pageReqVO 分页查询条件
     * @return 返回分页数据
     */
    @GetMapping("/page")
    @Operation(summary = "获得消息任务分页")
    public R<PageResult<MsgTaskBaseVO>> getMailAccountPage(@Valid MsgTaskPageReqVO pageReqVO) {
        PageResult<MsgTask> msgTaskPage = msgTaskService.getMsgTaskPage(pageReqVO);
        return R.ok(MsgTaskConvert.INSTANCE.convertPage(msgTaskPage));
    }

    /**
     * 快速查询所有的消息任务各表关系
     * @return 消息任务各表关系
     */
    @GetMapping("/list-all-simple")
    @Operation(summary = "获得消息任务精简列表")
    public R<List<MsgTaskSimpleRespVO>> getSimpleMailAccountList() {
        List<MsgTask> list = msgTaskService.getMsgTaskList();
        return R.ok(MsgTaskConvert.INSTANCE.convertList02(list));
    }
}
