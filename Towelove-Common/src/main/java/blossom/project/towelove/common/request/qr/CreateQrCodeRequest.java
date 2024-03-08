package blossom.project.towelove.common.request.qr;

import lombok.*;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.common.request.qr
 * @className: createQrCodeRequest
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/8 16:16
 * @version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateQrCodeRequest {

    /**
     * 二维码内容
     */
    private String data;

    /**
     * 1:网址
     * 2.图片
     * 3.文件
     */
    private Integer type;

    /**
     * 生成二维码类型
     */
    private String qrType = "png";

    /**
     * 是否需要统计
     */
    private boolean statistics = false;
}

