package blossom.project.towelove.server.service.impl;

import blossom.project.towelove.common.context.ApplicationContextHolder;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.request.qr.CreateQrCodeRequest;
import blossom.project.towelove.server.executor.QrCodeGenerator;
import blossom.project.towelove.server.service.QrService;
import cn.hutool.extra.qrcode.QrConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.service.impl
 * @className: QrServiceImpl
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/8 16:19
 * @version: 1.0
 */

@Service
@RequiredArgsConstructor
public class QrServiceImpl implements QrService {

    private final QrCodeGenerator qrCodeGenerator;

    @Override
    public String createQrCode(CreateQrCodeRequest request) {
        if (request.isStatistics()) {
            //TODO:统计相关操作
        }
        String data = "";
        try {
            data = qrCodeGenerator.generateQrCode(request.getData(), request.getQrType());
        } catch (IOException e) {
            throw new ServiceException("生成二维码失败,失败原因为" + e.getMessage());
        }
        return data;
    }
}
