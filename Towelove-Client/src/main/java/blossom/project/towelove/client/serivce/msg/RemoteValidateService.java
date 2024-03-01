package blossom.project.towelove.client.serivce.msg;

import blossom.project.towelove.client.fallback.RemoteValidateFallbackFactory;
import blossom.project.towelove.common.request.msg.ValidateCodeRequest;
import blossom.project.towelove.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.client.serivce.msg
 * @className: RemoteValidateService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/1 22:02
 * @version: 1.0
 */
@FeignClient(value = "towelove-msg",contextId = "RemoteValidateService",fallbackFactory = RemoteValidateFallbackFactory.class)
public interface RemoteValidateService {
    @PostMapping("/v1/msg/validate")
    Result<String> validate(@RequestBody ValidateCodeRequest request);

    @PostMapping("/v1/msg/validate/multi")
    Result<String> validateMulti(@RequestBody List<ValidateCodeRequest> validateCodeRequests);
}
