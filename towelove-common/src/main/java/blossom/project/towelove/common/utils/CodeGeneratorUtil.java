package blossom.project.towelove.common.utils;

import java.util.Random;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/22 20:27
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * CodeGeneratorUtil类
 */
public class CodeGeneratorUtil {
    /**
     * 非常简单的一个生成验证码的方法
     * @return
     */
    public static String generateFourDigitCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // 生成 1000 到 9999 之间的随机数
        return String.valueOf(code);
    }
}
