package blossom.project.towelove.common.request.todoList;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 上午10:22 1/12/2023
 */
@Data
public class UpdateTodoRequest {


    @NotNull(message = "id不能为空")
    private Long id;

    private Long parentId;

    private Long coupleId;

    private String title;

    private String description;

    private String deadline;

    private String priority;

    private List<String> images;

}
