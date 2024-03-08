package blossom.project.towelove.server.controller;

import blossom.project.towelove.common.request.qr.CreateQrCodeRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.server.service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.controller
 * @className: QrController
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/8 16:13
 * @version: 1.0
 */
@RestController
@RequestMapping("/qr")
@RequiredArgsConstructor
public class QrController {

    private final QrService qrService;

    @PostMapping("")
    public Result<String> createQrCode(@RequestBody @Validated CreateQrCodeRequest request){
        return Result.ok(qrService.createQrCode(request));
    }
}
