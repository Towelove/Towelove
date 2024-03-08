package blossom.project.towelove.client.serivce.file;

import blossom.project.towelove.client.fallback.RemoteQrCodeFallbackFactory;
import blossom.project.towelove.common.request.qr.CreateQrCodeRequest;
import blossom.project.towelove.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.client.serivce.file
 * @className: RemoteQrCodeService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/8 16:47
 * @version: 1.0
 */
@FeignClient(value = "towelove-server-center",contextId = "remoteQrCodeService"
        ,fallbackFactory = RemoteQrCodeFallbackFactory.class)
public interface RemoteQrCodeService {
    @PostMapping("/v1/server/center/qr")
    public Result<String> createQrCode(CreateQrCodeRequest qrCodeRequest);
}
