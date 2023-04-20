package com.towelove.monitor.test.excel;

import org.apache.logging.log4j.util.Strings;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author: 张锦标
 * @date: 2023/4/20 15:04
 * ClassParse类
 */
public class ClassParse {
    public static void main(String[] args) {
        String excelPath = "D:\\desktop\\学生个人课表_21408200233.xls";
        long beginTime = System.currentTimeMillis();
        try {
            //String encoding = "GBK";
            File excel = new File(excelPath);
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    System.out.println("文件类型错误!");
                    return;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum() + 3;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
//                System.out.println("firstRowIndex: "+firstRowIndex);
//                System.out.println("lastRowIndex: "+lastRowIndex);
                //获得班级信息
                Row clazzName = sheet.getRow(sheet.getFirstRowNum() + 1);
                Cell clazzInfoCell = clazzName.getCell(clazzName.getFirstCellNum());
                System.out.println(clazzInfoCell);
                String clazzInfo = getclazzName(clazzInfoCell);
                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
//                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum() + 1;
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            row.getCell(cIndex);
                            if (cell != null && Strings.isNotBlank(cell.toString())) {
//                                System.out.println(cell.toString());
//                                System.out.println("日期----》"+cIndex);
//                                System.out.println("节-----》"+ (rIndex - 2));
                                toPoJo(cell, cIndex, rIndex, clazzInfo);
                            }
                        }
                    }
                }
                long endTime = System.currentTimeMillis();
                System.out.println("程序运行时间------>" + (endTime - beginTime));
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getclazzName(Cell clazzInfoCell) {
        String clazzInfo = clazzInfoCell.toString();

        int firstIndex = clazzInfo.indexOf("班级：");
        int lastIndex = clazzInfo.indexOf("专业：");
        String substring = clazzInfo.substring(firstIndex, lastIndex);
        String[] split = substring.split("：");
        String result = split[1].replace(" ", "");
        return result;
    }


    private static void toPoJo(Cell cell, int cIndex, int rIndex, String clazzName) {
        String scheduleInfo = cell.toString();

        String s2 = scheduleInfo.replaceAll("[\b\r\n\t]", " ").trim();
        //System.out.println(s2);
        String[] s1 = s2.split(" ");
        //System.out.println(Arrays.toString(s1));
        int num = 1;
        for (int i = 0; i < s1.length; i++) {
            if (StringUtils.isEmpty(s1[i])){
                num++;
            }
        }
        System.out.println(num);
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setTimeStart(rIndex - 2);
        classSchedule.setTimeEnd(rIndex - 1);
        classSchedule.setDay(cIndex);
        classSchedule.setClazzName(clazzName);
        for (int i=0;i<num;i++){
            classSchedule.setTitle(s1[i*6]);
            classSchedule.setTeacher(s1[i*6+1]);
            classSchedule.setWeek(s1[i*6+2]);
            classSchedule.setLocation(s1[i*6+3]);
            //TODO 插入数据库
            System.out.println(classSchedule);
        }


        //String[] infos = scheduleInfo.split("\n");
        //classSchedule.setTitle(infos[1]);
        //classSchedule.setTeacher(infos[2]);
        //classSchedule.setWeek(infos[3]);
        //classSchedule.setLocation(infos[4]);


        //TODO 插入数据库
    }
}
