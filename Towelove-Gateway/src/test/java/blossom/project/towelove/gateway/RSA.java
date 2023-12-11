package blossom.project.towelove.gateway;


import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

//@SpringBootTest
class RSA {



    public static void main(String[] args) throws Exception {
        //TODO 2：这里得到的是获取rsa的公钥之后，对对称密钥进行加密，之后就是使用这个对称密钥进行
        //数据的加解密
        // Replace with your RSA public key
        String publicKeyPEM = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvXBSqSyOPb01/uOnhnFN8Hvaz1IQbXnxFzGp9rWBxRAI2p6o67Elr1+SW68JnXx4swq7+z0U+YZSuszsoqwIrn8XF75bpJ+NKLkH7Bpe5A+If78zTihsCoPs+x74FIaJTSiVCzWP9mCaDSVO2bPTwOvqMwQ7xlmTmN9QShCIJ6uBXaggB5aWdpkh/IsIsZXIlzFB5HxA8AYj3u0AyWZO+pNS1fwq2Q7GPwWG7Zl7bCrUjIbG40k/Ef1BjdJBhQakMUq3Zqx+LJP37Tk4FzW47bwD9AiSL4DAXT+sc+Hw1fNspd2qFZBN94h5Pxkxoc9ZBMWB2bFBdRb6zkEg0/2OwwIDAQAB" ;

        // Replace with your symmetric key
        String symmetricKey = "zhangjinbiao6666";

        // Converting PEM to PublicKey
        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // Encrypting symmetric key
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedSymmetricKey = cipher.doFinal(symmetricKey.getBytes());
        String encryptedSymmetricKeyBase64 = Base64.getEncoder().encodeToString(encryptedSymmetricKey);

        // Printing encrypted symmetric key
        System.out.println(encryptedSymmetricKeyBase64);
    }

}