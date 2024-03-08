package blossom.project.towelove.server.executor;

import blossom.project.towelove.common.exception.ServiceException;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.ServerError;
import java.util.Optional;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.executor
 * @className: QrCodeGenerator
 * @author: Link Ji
 * @description: GOGO
 * @date: 2024/3/8 16:21
 * @version: 1.0
 */
@Component
@RequiredArgsConstructor
public class QrCodeGenerator {

    private final String QRCODE_PREFIX = "data:image/jpg;Base64,%s";
    private  QrConfig qrConfig;

    {
        try {
            qrConfig = QrConfig.create()
                    //背景 粉色
                    .setBackColor(Color.pink)
                    //高度宽度
                    .setHeight(300)
                    .setWidth(300)
                    .setImg(ResourceUtils.getFile("classpath:logo.jpg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //logo
//                .setImg("");;

    public String generateQrCode(String data,String type) throws IOException {
        type = Optional.ofNullable(type).orElse("png");
        BufferedImage image = QrCodeUtil.generate(data, qrConfig);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (!ImageIO.write(image,type,outputStream)) {
            throw new ServiceException("写入数据失败");
        }
        //获得字节数据，转化BASE64编码
        byte[] byteArray = outputStream.toByteArray();
        return String.format(QRCODE_PREFIX, Base64.encode(byteArray));
    }

    /**
     * 默认生成PNG
     * @param data
     * @return
     * @throws IOException
     */
    public String generateQrCode(String data) throws IOException {
        return generateQrCode(data,"png");
    }
}
