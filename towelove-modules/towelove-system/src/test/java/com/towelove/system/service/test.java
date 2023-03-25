import lombok.SneakyThrows;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Zhangjinbiao
 * @Date: 2023/1/1 13:33
 * @Connection: qq460219753 wx15377920718
 * Description:
 * Version: 1.0.0
 */

public class test {
    /**
     * 加密方法
     * @param buffer
     * @return
     */
    public static byte[] xor(byte[] buffer) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] ^= 100;
        }
        return buffer;
    }

    /**
     *
     * @param file2Encrypt 准备加密的文件位置
     * @param destination 加密后文件的位置
     */
    @SneakyThrows
    public static void encrypt(File file2Encrypt,File destination){
        FileInputStream fis = new FileInputStream(file2Encrypt);
        FileOutputStream fos = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        while (fis.available() > 0) {
            fis.read(buffer);
            buffer = xor(buffer); //加密
            fos.write(buffer, 0, buffer.length); //写入到文件中
        }
        fis.close();
        fos.close();
        System.out.println("加密成功，记住解密的时候设定一个新的位置放文件");
    }

    /**
     * 解密方法
     * @param fileHasEncrypted 加密后的文件
     * @param destination 解密后文件的位置
     */
    @SneakyThrows
    public static void decode(File fileHasEncrypted,File destination){
        FileInputStream fis = new FileInputStream(fileHasEncrypted);
        FileOutputStream fos = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        while (fis.available() > 0) {
            fis.read(buffer);
            buffer = xor(buffer); //加密
            fos.write(buffer, 0, buffer.length); //写入到文件中
        }
        fis.close();
        fos.close();
        System.out.println("解密成功");
    }
    public static void main(String[] args) throws IOException {
        File enc = new File("D://desktop//123.mp4");//准备加密的文件
        File dest = new File("D://desktop//1234.mp4");//加密后文件位置
        File dest1 = new File("D://desktop//12345.mp4");//加密后文件位置
      //  encrypt(enc,dest); //先加密
        decode(dest,dest1); //再解密
    }
}
