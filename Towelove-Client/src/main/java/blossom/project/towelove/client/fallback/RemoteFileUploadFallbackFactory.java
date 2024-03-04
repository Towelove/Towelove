package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.file.RemoteFileUploadService;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.handler.SecurityConstant;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.client.fallback
 * @className: RemoteFileUploadFallbackFactory
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/4 22:01
 * @version: 1.0
 */
@Component
public class RemoteFileUploadFallbackFactory implements FallbackFactory<RemoteFileUploadService>{
    @Override
    public RemoteFileUploadService create(Throwable cause) {
        return new RemoteFileUploadService() {

            @Override
            public Result<String> upload(MultipartFile file) {
                return Result.fail(null, SecurityConstant.REQUEST_ID);
            }
        };
    }
}
