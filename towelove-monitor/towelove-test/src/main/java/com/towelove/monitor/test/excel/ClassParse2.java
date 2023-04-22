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
import java.util.Arrays;

/**
 * @author: 张锦标
 * @date: 2023/4/20 15:04
 * ClassParse类
 */
public class ClassParse2 {
    public static void main(String[] args) {
        parseToClassSchedule("D:\\desktop\\table.xlsx","2102班");
    }
    public static void parseToClassSchedule(String filePath,String clazzName) {
        String excelPath = filePath;
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

                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum()-1;
//                System.out.println("firstRowIndex: "+firstRowIndex);
//                System.out.println("lastRowIndex: "+lastRowIndex);
                //获得班级信息
                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
//                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        //获取从星期一开始的那一列
                        int firstCellIndex = row.getFirstCellNum() + 1;
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            row.getCell(cIndex);
                            if (cell != null && Strings.isNotBlank(cell.toString())) {
                                toPoJo(cell, cIndex, rIndex, clazzName);
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

    private static void toPoJo(Cell cell, int cIndex, int rIndex, String clazzName) {
        String s = cell.toString();
        String p = s.replaceAll(" P", "");
        String s2 = p.replaceAll(" ---------------------", " ");
        String[] s1 = s2.split(" ");
        //System.out.println(Arrays.toString(s1));
        int num=1;
        for (int i = 0; i < s1.length; i++) {
            if (StringUtils.isEmpty(s1[i])){
                num++;
            }
        }
        //System.out.println(num);

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
