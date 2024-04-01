package blossom.project.towelove.server.executor;

import blossom.project.towelove.common.exception.ServerException;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.exception.errorcode.IErrorCode;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
@Slf4j
public class QrCodeGenerator {

    private final String QRCODE_PREFIX = "data:image/jpg;Base64,%s";
    private final QrConfig qrConfig;

    public QrCodeGenerator() {
        qrConfig = new QrConfig(300, 300);
        qrConfig.setBackColor(Color.LIGHT_GRAY);
        File file = null;
        try {
            file = File.createTempFile("logo", ".jpg");
            URL url = new URL("https://oss.towelove.cn/towelove-images/2024/03/08/微信图片_20240308182109_20240308182349A003.jpg");
            BufferedImage image = ImageIO.read(url);
            ImageIO.write(image, "jpg", file);
            qrConfig.setImg(file);
        } catch (IOException e) {
            log.error("启动二维码生成策略时，获取logo图失败");
            throw new ServerException("启动二维码生成策略时，获取logo图失败", BaseErrorCode.SERVICE_ERROR);
        }finally {
            FileUtil.del(file);
        }
    }
//                .setImg("");;

    public String generateQrCode(String data, String type) throws IOException {
        type = Optional.ofNullable(type).orElse("png");
        BufferedImage image = QrCodeUtil.generate(data, qrConfig);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            if (!ImageIO.write(image, type, outputStream)) {
                throw new ServiceException("写入数据失败");
            }
            //获得字节数据，转化BASE64编码
            byte[] byteArray = outputStream.toByteArray();
            return String.format(QRCODE_PREFIX, Base64.encode(byteArray));
        }
    }

    /**
     * 默认生成PNG
     *
     * @param data
     * @return
     * @throws IOException
     */
    public String generateQrCode(String data) throws IOException {
        return generateQrCode(data, "png");
    }
}
