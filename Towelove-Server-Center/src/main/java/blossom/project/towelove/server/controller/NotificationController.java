package blossom.project.towelove.server.controller;

import blossom.project.towelove.common.entity.notification.Notification;
import blossom.project.towelove.common.request.PullNotifyRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.server.redisMQ.UserNotifyConstants;
import blossom.project.towelove.server.redisMQ.UserNotifyProduction;
import blossom.project.towelove.server.service.NotificationService;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

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

    private final UserNotifyProduction userNotifyProduction;
    @GetMapping("")
    public DeferredResult<Result<?>> pullNotify(@RequestParam(required = true) String requestId
            , PullNotifyRequest pullNotifyRequest
            , @RequestParam(required = false,defaultValue = "10000") Long timeOut){
        return notificationService.pullNotify(requestId,pullNotifyRequest,timeOut);
    }

    @PostMapping("")
    public void test(){
        Notification notification = new Notification();
        notification.setRequestId("123123");
        notification.setMessage("【您有新通知！！！】Hello,world");
        userNotifyProduction.sendMessage(notification);
    }
}
