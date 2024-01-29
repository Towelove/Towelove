package blossom.project.towelove.client.serivce.msg;

import blossom.project.towelove.client.fallback.RemoteMsgFallbackFactory;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@FeignClient(value = "towelove-msg",
        fallbackFactory = RemoteMsgFallbackFactory.class,
        contextId = "RemoteMsgService")
public interface RemoteMsgService {

    /**
     * 按照ID查询
     *
     * @param msgTaskId
     * @return
     */
    @GetMapping("")
    Result<MsgTaskResponse>
    getMsgTaskById(@Validated @RequestParam(name = "msgTaskId")
                   @NotNull(message = "msgTaskId can not be null!")
                   Long msgTaskId);

    /**
     * 带条件分页查询
     *
     * @param requestParam
     * @return
     */
    @GetMapping("/page")
    Result<PageResponse<MsgTaskResponse>>
    pageQueryMsgTask(@Validated @RequestBody MsgTaskPageRequest requestParam);

}
