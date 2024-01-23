package blossom.project.towelove.client.serivce;

import blossom.project.towelove.common.request.msg.MsgTaskCreateRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 14:30 2024/1/23
 */

@FeignClient(value="towelove-msg", path="towelove-msg")
@RequestMapping("/v1/msg-task")
public interface MsgTaskService {

    /**
     * 创建
     * @param createRequest
     * @return
     */
    @PostMapping("")
    Result<MsgTaskResponse> createMsgTask(@RequestBody @Valid MsgTaskCreateRequest createRequest);


    /**
     * 根据ID批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/batch")
    Result<Boolean> batchDeleteMsgTask(@RequestBody List<Long> ids);


    /**
     * 按照ID查询
     * @param msgTaskId
     * @return
     */
    @GetMapping("")
    Result<MsgTaskResponse> getMsgTaskById(@Validated @RequestParam(name = "msgTaskId")
                                                  @NotNull(message = "msgTaskId can not be null!")
                                                  Long msgTaskId);

}
