package blossom.project.towelove.server.service;

import blossom.project.towelove.common.request.PullNotifyRequest;
import blossom.project.towelove.common.response.Result;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.service
 * @className: NotificationService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 19:00
 * @version: 1.0
 */
public interface NotificationService {

    DeferredResult<Result<?>> pullNotify(String requestId, PullNotifyRequest pullNotifyRequest, Long timeOut);
}
