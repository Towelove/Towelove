//package com.towelove.monitor.test.excel;
//
//import java.util.Arrays;
//
///**
// * @author: 张锦标
// * @date: 2023/4/18 14:07
// * ExcelTest1类
// */
//public class ExcelTest1 {
//    public static void main(String[] args) {
//        String s = "面向对象程序设计\n" +
//                "杨振亚(讲师)\n" +
//                "1-11[周]\n" +
//                "公共422\n" +
//                "[09-10]节";
//        String[] split = s.split("\\n");
//        classSchedule1 classSchedule1 = new classSchedule1();
//        classSchedule1.setClassName(split[0]);
//        classSchedule1.setTeacher(split[1]);
//        classSchedule1.setStartWeek(split[2]);
//        classSchedule1.setClassroom(split[3]);
//        classSchedule1.setDay(3);
//        classSchedule1.setStart(9);
//        classSchedule1.setEnd(11);
//        System.out.println(classSchedule1);
//        //System.out.println(Arrays.toString(split));
//
//        String s1 = "学年学期：2022-2023-2        班级：电子信息2004        专业：电子信息工程        学院：电气与信息工程学院        打印日期：2023-04-07";
//        String[] s2 = s1.split("        ");
//        String[] titleInfo = new String[5];
//        int i =0;
//        for (String s3 : s2) {
//            String[] split1 = s3.split("：");
//            titleInfo[i++] = split1[1];
//            //System.out.println(split1[1]);
//        }
//        ClassTitle classTitle = new ClassTitle();
//        classTitle.setYeadSemeter(titleInfo[0]);
//        classTitle.setClassGrade(titleInfo[1]);
//        classTitle.setCollege(titleInfo[2]);
//        classTitle.setMajor(titleInfo[3]);
//        classTitle.setRemark("备注信息直接从最后一行最后一列获取，也就是r=8，c=1");
//        System.out.println(classTitle);
//
//        //System.out.println(Arrays.toString(s2));
//        //System.out.println(Arrays.toString(s3));
//    }
//}
