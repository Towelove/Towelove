package blossom.project.towelove.common.request.todoList;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 上午10:22 1/12/2023
 */
@Data
public class TodoListUpdateRequest {


    @NotNull(message = "id不能为空")
    private Long id;

    private Long coupleId;

    private String title;

    private String description;

    private String deadline;

    /**
     * 是否是小组件
     */
    private Boolean weight;
}
