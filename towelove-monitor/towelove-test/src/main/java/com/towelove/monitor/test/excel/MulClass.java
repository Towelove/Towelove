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
        String s = "就业指导 夏叶文编审 4,6-7(周)[09-10节] 公共423 --------------------- 就业指导 P 夏叶文编审 8(周)[09-10节] 公共423";
        String p = s.replaceAll(" P", "");
        String s2 = p.replaceAll(" ---------------------", " ");
        String[] s1 = s2.split(" ");
        System.out.println(Arrays.toString(s1));
        int num=1;
        for (int i = 0; i < s1.length; i++) {
            if (StringUtils.isEmpty(s1[i])){
                num++;
            }
        }
        System.out.println(num);
        int rIndex=1;
        int cIndex=1;
        String clazzName = "2002班";
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setTimeStart(rIndex * 2 -1); //上课开始
        classSchedule.setTimeEnd(rIndex * 2);
        classSchedule.setDay(cIndex);
        classSchedule.setClazzName(clazzName);
        for (int i=0;i<num;i++){
            classSchedule.setTitle(s1[i*5]);
            classSchedule.setTeacher(s1[i*5+1]);

            String week = s1[i * 5 + 2];
            String weekfrom = week.substring(0, week.indexOf("("));
            classSchedule.setWeek(weekfrom);
            classSchedule.setLocation(s1[i*5+3]);
            //TODO 插入数据库
            System.out.println(classSchedule);
        }
    }
}
