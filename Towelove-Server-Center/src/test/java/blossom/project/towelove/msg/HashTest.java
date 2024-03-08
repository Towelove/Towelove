package blossom.project.towelove.msg;

import blossom.project.towelove.common.exception.ServiceException;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.aliyuncs.utils.IOUtils;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigInteger;
import java.util.Random;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.shortUrl
 * @className: HashTest
 * @author: Link Ji
 * @description:
 * @date: 2023/12/2 15:55
 * @version: 1.0
 */
public class HashTest {
    @Test
    void hash(){
        String key = "https://wenku.baidu.com/view/ccf149235223482fb4daa58da0116c175f0e1ef9.html?_wkts_=1701499963315";
        int hash = Math.abs(HashUtil.murmur32(key.getBytes()));
        System.out.println(hash);
        int index = hash % 10;
        System.out.println(index);
        String url = Integer.toString(hash, 32);
        System.out.println(url);
        String convertHash = new BigInteger(url,32).toString(10);
        System.out.println(convertHash);
        System.out.println(Integer.parseInt(convertHash) % 10);

    }
    private String convert2Base62(long hash){
        StringBuilder sb = new StringBuilder();
        while (hash > 0){
            sb.append(hash % 62);
            hash /= 62;
        }
        return sb.toString();
    }

    @Test
    void netTest(){
        byte[] localHardwareAddress = NetUtil.getLocalHardwareAddress();

    }


    @Test
    void qrTest(){
        String invitedCode = RandomUtil.randomString(5);
        String path = "D:\\qrCode\\";
        if (!FileUtil.exist(path)) {
            FileUtil.mkdir(path);
        }
        try {
            BufferedImage image = QrCodeUtil.generate("https://web.towelove.cn/?invitedCode=" + invitedCode, 300, 300);
            ByteArrayOutputStream outputStream  = new ByteArrayOutputStream();
            ImageIO.write(image,"png",outputStream);
            byte[] qrCodeByte = outputStream.toByteArray();
            String encode = Base64.encode(qrCodeByte);
            System.out.println(encode);
        }catch (Exception e){
            throw new ServiceException("生成二维码失败");
        }


    }
    @Test
    void test111(){
        File file = new File("src/main/resource/logo.jpg");
        System.out.println(file);
    }
}
