package blossom.project.towelove.client.serivce.file;

import blossom.project.towelove.client.fallback.RemoteFileUploadFallbackFactory;
import blossom.project.towelove.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.client.serivce.file
 * @className: FileUploadService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/4 21:59
 * @version: 1.0
 */
@FeignClient(value = "towelove-loves",contextId = "FileUploadService"
        ,fallbackFactory = RemoteFileUploadFallbackFactory.class)
public interface RemoteFileUploadService {
    @PostMapping(value = "/v1/loves/oss/file",consumes = {"multipart/form-data"})
     Result<String> upload(@RequestPart("file") MultipartFile file);
}
