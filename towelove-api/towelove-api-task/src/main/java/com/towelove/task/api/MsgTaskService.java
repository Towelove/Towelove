package com.towelove.task.api;

import com.towelove.common.core.domain.R;
import com.towelove.task.api.factory.MsgTaskFallbackFactory;
import com.towelove.task.api.model.MsgTask;
import com.towelove.task.api.vo.MsgTaskSimpleRespVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:季台星
 * @Date：2023-3-12 12:10
 */
@FeignClient(value = "msg-task",fallbackFactory = MsgTaskFallbackFactory.class)
@RequestMapping("/msg/task")
public interface MsgTaskService {
   @GetMapping("/get")
   R<MsgTask> getMailAccount(@RequestParam("id") Long id);
    @GetMapping("/list-all-simple")
    @Operation(summary = "获得消息任务精简列表")
    public R<List<MsgTaskSimpleRespVO>> getSimpleMailAccountList();

}
