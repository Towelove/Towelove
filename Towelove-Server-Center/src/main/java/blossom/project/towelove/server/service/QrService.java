package blossom.project.towelove.server.service;

import blossom.project.towelove.common.request.qr.CreateQrCodeRequest;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.service
 * @className: QrService
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/8 16:19
 * @version: 1.0
 */
public interface QrService {
    String createQrCode(CreateQrCodeRequest request);
}
