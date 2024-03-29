package blossom.project.towelove.common.request.todoList;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 上午10:22 1/12/2023
 */
@Data
public class TodoListUpdateRequest {


    @NotNull(message = "id不能为空")
    private Long id;


    private String title;

    private String description;

    //截止日期
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;

    //完成日期
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completionDate;
    /**
     * @Comment("是否提醒")
     */
    private Boolean reminder;
    /**
     * 是否是小组件
     */
    private Boolean weight;

    /**
     * 完成状态  1完成 0未完成
     */
    private Integer status;

}
