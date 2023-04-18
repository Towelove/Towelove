package com.towelove.monitor.test.excel;

import lombok.Data;

/**
 * @author: 张锦标
 * @date: 2023/4/18 14:12
 * ClassSchedule类
 */
@Data
public class ClassSchedule {
    /**
     * 表id
     */
    private Long id;
    /**
     * 课程标题id
     */
    private Long classTitleId;
    /**
     * 第几节课到第几节课
     */
    private Integer start;
    private Integer end;
    /**
     * 星期几
     */
    private Integer day;
    /**
     * 课程名称
     */
    private String className;
    /**
     * 老师名称
     */
    private String teacher;
    /**
     * 开始和结束的周数
     * 1  1-10
     */
    private String startWeek;
    /**
     * 班级号
     *
     */
    private String classroom;
}
