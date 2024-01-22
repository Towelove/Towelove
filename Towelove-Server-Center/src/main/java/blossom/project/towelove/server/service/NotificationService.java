package blossom.project.towelove.server.service;

import blossom.project.towelove.common.request.NoticeRequest;
import blossom.project.towelove.common.request.PullNotifyRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.server.dto.NoticeVO;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

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

    DeferredResult<Result<?>> pullNotify(Long requestId, PullNotifyRequest pullNotifyRequest, Long timeOut);

    Result<List<NoticeVO>> pullAll(Long userId);


    Result create(NoticeRequest notificationService);
}
