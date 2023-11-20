package com.towelove.system.api.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author wangLele
 */
@Data
public class UploadImgRequest {

    @ApiModelProperty("图片")
    private MultipartFile file;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("相册ID")
    private Long albumId;
}
