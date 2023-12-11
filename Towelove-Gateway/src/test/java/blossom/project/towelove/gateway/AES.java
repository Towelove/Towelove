package blossom.project.towelove.gateway;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author: 张锦标
 * @date: 2023/10/2 17:32
 * AES类
 */
public class AES {
    //1:首先让前端对请求路径传输进行AES的加密 密钥已经传递
    //比如productId=1 ---》wHYOLLkTn00DVrcmuCFzFQ==
    //如果有多个 就直接 & 的方式进行拼接然后AES加密即可
    //2：signature=wHYOLLkTn00DVrcmuCFzFQ==
    //3：然后在对整个URL进行加密传输，传输方式为 /encrypt +
    // /5s7/98nWOXAJKujQ7nj66ZhohFdur/pPBzd3Y9kZqeIrZmPvTegG8
    // +OYwY6IMr9dXtK9vmZvJoEEsWZT+LLBCQ==
    //其中 + 后面的就是我们aes加密后的url ，/encrypt用于表示进行前端的路由

    public static void main(String[] args) throws Exception {
        //TODO 1：首先设定一下加密的内容 这里直接用java代码加密
        String plaintext = "productId=1";
        //String plaintext = "productId=1&signature=wHYOLLkTn00DVrcmuCFzFQ==";
        String symmetricKey = "zhangjinbiao6666";  // Ensure this key has 16 bytes

        String encryptedText = encryptUrl(plaintext, symmetricKey);
        System.out.println(encryptedText);
    }

    public static String encryptUrl(String url, String symmetricKey) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(symmetricKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(url.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

}