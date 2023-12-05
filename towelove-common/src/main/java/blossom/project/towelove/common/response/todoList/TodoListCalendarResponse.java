package blossom.project.towelove.common.response.todoList;

import lombok.Data;

import java.util.Date;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 上午11:15 5/12/2023
 */
@Data
public class TodoListCalendarResponse {
    private Long id;
    private String title;
    //截止日期
    private Date deadline;
    //完成状态
    private Integer completionStatus;
}
