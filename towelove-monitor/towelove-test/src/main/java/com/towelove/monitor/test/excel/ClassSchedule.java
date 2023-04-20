package com.towelove.monitor.test.excel;

import lombok.Data;

/**
 * @author: 张锦标
 * @date: 2023/4/20 15:02
 * ClassINfo类
 */
@Data
public class ClassSchedule {
    private Long id;
    private String week;
    private Integer day;
    private Integer timeStart;
    private Integer timeEnd;

    private String title;
    private String location;
    private String teacher;
    private String clazzName;
}
