package blossom.project.towelove.common.response.todoList;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 下午2:57 4/12/2023
 */
@Data
public class TodoListResponse {
    private Long id;
    private String title;
    private String content;
    //描述
    private String description;
    //截止日期
    private Date deadline;
    //完成状态
    private Integer completionStatus;
    //完成日期
    private Date completionDate;

    List<TodoListResponse> children;
}
