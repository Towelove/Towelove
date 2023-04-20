package com.towelove.monitor.test.excel;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author: 张锦标
 * @date: 2023/4/20 14:55
 * MulClass类
 */
public class MulClass {
    public static void main(String[] args) {
        String s = "概率论与数理统计\n" +
                "董宁(副教授)\n" +
                "1-6[周]\n" +
                "公共105\n" +
                "[09-10]节\n" +
                "\n" +
                "电路与电子学\n" +
                "刘颖慧(副教授)\n" +
                "1-10[周]\n" +
                "公共109\n" +
                "[09-10]节\n";
        String s2 = s.replaceAll("[\b\r\n\t]", " ");
        System.out.println(s2);
        String[] s1 = s2.split(" ");
        System.out.println(Arrays.toString(s1));
        for (String s3 : s1) {
            if (StringUtils.isEmpty(s3)){
                System.out.println("---"+s3+"---");
            }
        }
    }
}
