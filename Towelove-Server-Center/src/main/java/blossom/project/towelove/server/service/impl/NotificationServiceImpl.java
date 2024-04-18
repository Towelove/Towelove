package blossom.project.towelove.server.service.impl;

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.NoticeRequest;
import blossom.project.towelove.common.request.PullNotifyRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.server.dto.NoticeVO;
import blossom.project.towelove.server.entity.Notice;
import blossom.project.towelove.server.mapper.NoticeMapper;
import blossom.project.towelove.server.mq.redis.UserNotifyDeferredCache;
import blossom.project.towelove.server.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.function.Consumer;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.service.impl
 * @className: NotificationServiceImpl
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/1/19 19:00
 * @version: 1.0
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserNotifyDeferredCache userNotifyDeferredCache;
    private final NoticeMapper noticeMapper;
    @Override
    public DeferredResult<Result<?>> pullNotify(Long userId, PullNotifyRequest pullNotifyRequest, Long timeOut) {
        //使用DeferredResult来执行异步操作
        DeferredResult<Result<?>> deferredResult = new DeferredResult<>(timeOut);
        //超时情况下
        deferredResult.onTimeout(()->{
            //从MAP中删除
            userNotifyDeferredCache.remove(userId);
            deferredResult.setResult(Result.ok("暂无最新消息"));
        });
        userNotifyDeferredCache.save(userId,deferredResult::setResult);
        return deferredResult;
    }

    @Override
    public Result<List<NoticeVO>> pullAll(Long userId) {
        List<NoticeVO> noticeVOS = noticeMapper.selectNoticeByUserId(userId);
        return Result.ok(noticeVOS);
    }

    public void setDeferredResult(Long requestId, String message){
        if (userNotifyDeferredCache.isExist(requestId)){
            Consumer<Result<?>> consumer = userNotifyDeferredCache.remove(requestId);
            consumer.accept(Result.ok(message));
        }
    }

    @Override
    public Result create(NoticeRequest noticeRequest) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeRequest,notice);
        if (noticeMapper.insert(notice) < 1) {
            throw new ServiceException("新增系统通知失败");
        }
        return Result.ok("新增系统通知消息成功");
    }
}
