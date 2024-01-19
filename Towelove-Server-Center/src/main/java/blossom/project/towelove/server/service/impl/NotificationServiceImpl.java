package blossom.project.towelove.server.service.impl;

import blossom.project.towelove.common.request.PullNotifyRequest;
import blossom.project.towelove.common.response.PullNotifyResponse;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.server.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
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
    private Map<String, Consumer<Result<?>>> resultMap = new ConcurrentHashMap<>();
    @Override
    public DeferredResult<Result<?>> pullNotify(String requestId, PullNotifyRequest pullNotifyRequest, Long timeOut) {
        //使用DeferredResult来执行异步操作
        DeferredResult<Result<?>> deferredResult = new DeferredResult<>(timeOut);
        //超时情况下
        deferredResult.onTimeout(()->{
            //从MAP中删除
            resultMap.remove(requestId);
            Result result = new Result();
            result.setCode(206);
            result.setMsg("暂无新消息通知");
            deferredResult.setResult(result);
        });
        Optional.ofNullable(resultMap)
                .filter(map -> !map.containsKey(requestId))
                .orElseThrow(() -> new IllegalArgumentException(String.format("requestId=%s is existing", requestId)));
        resultMap.putIfAbsent(requestId,deferredResult::setResult);
        return deferredResult;
    }

    public void setDeferredResult(String requestId,String message){
        if (resultMap.containsKey(requestId)){
            Consumer<Result<?>> consumer = resultMap.remove(requestId);
            consumer.accept(Result.ok(message));
        }
    }
}
