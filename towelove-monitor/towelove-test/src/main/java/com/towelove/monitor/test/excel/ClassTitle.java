package com.towelove.monitor.test.excel;

import lombok.Data;

/**
 * @author: 张锦标
 * @date: 2023/4/18 14:36
 * ClassTitle类
 */
@Data
public class ClassTitle {
    private Long id;
    /**
     * 班级号
     */
    private String classGrade;
    /**
     * 学年学期
     */
    private String yeadSemeter;
    /**
     * 专业
     */
    private String major;
    /**
     * 学院
     */
    private String college;
    /**
     * 课程备注
     */
    private String remark;
}
