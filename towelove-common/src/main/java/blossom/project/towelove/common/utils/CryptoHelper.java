package blossom.project.towelove.common.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.MultiValueMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;


/**
 * @author 张锦标
 * 密码学工具包
 */
@Configuration
public class CryptoHelper {

    public String decryptUrl(String encryptedUrl, String symmetricKey) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(symmetricKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedUrl));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    //解析路径参数并且加密，后判断是否和signature一样
    public boolean verifySignature(MultiValueMap<String, String> queryParams, String signature, String symmetricKey) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            //将签名本身从要验证的数据中排除
            if (!"signature".equals(entry.getKey())) {
                sb.append(entry.getKey()).append("=").append(String.join(",", entry.getValue())).append("&");
            }
        }
        sb.setLength(sb.length()-1);
        String computedSignature = encryptRequestParam(sb.toString(), symmetricKey);
        return computedSignature.equals(signature);
    }


    public static String encryptRequestParam(String requestParam, String symmetricKey) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(symmetricKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(requestParam.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}