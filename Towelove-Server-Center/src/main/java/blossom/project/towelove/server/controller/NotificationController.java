package blossom.project.towelove.server.controller;

import blossom.project.towelove.common.request.NoticeRequest;
import blossom.project.towelove.common.request.PullNotifyRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.server.dto.NoticeVO;
import blossom.project.towelove.server.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.controller
 * @className: NotifycationController
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 18:09
 * @version: 1.0
 */
@RestController
@RequestMapping("/v1/notify")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping("")
    public DeferredResult<Result<?>> pullNotice(@RequestParam(required = true) Long userId
            , PullNotifyRequest pullNotifyRequest
            , @RequestParam(required = false,defaultValue = "10000") @Validated @Max(value = 60000L,message = "拉取消息延时必须小于60秒") Long timeOut){
        return notificationService.pullNotify(userId,pullNotifyRequest,timeOut);
    }

    @GetMapping("/all")
    public Result<List<NoticeVO>> pullAllNotice(@RequestParam("userId") Long userId){
        return notificationService.pullAll(userId);
    }

    @PostMapping("")
    public Result create(@Validated NoticeRequest noticeRequest){
        //管理员新增系统全体通知
        //TODO管理员权限
        return notificationService.create(noticeRequest);
    }
}
