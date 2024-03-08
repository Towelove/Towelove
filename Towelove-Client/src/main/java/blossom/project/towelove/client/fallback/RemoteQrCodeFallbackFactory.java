package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.file.RemoteQrCodeService;
import blossom.project.towelove.common.request.qr.CreateQrCodeRequest;
import blossom.project.towelove.common.response.Result;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.client.fallback
 * @className: RemoteQrCodeFallbackFactory
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/8 16:51
 * @version: 1.0
 */
@Component
public class RemoteQrCodeFallbackFactory implements FallbackFactory<RemoteQrCodeService> {
    @Override
    public RemoteQrCodeService create(Throwable cause) {
        return new RemoteQrCodeService() {
            @Override
            public Result<String> createQrCode(CreateQrCodeRequest qrCodeRequest) {
                return Result.fail("生成二维码失败");
            }
        };
    }
}
