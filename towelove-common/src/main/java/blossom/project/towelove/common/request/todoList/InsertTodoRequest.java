package blossom.project.towelove.common.request.todoList;

import lombok.Data;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.util.Date;

//@ApiModel(description = "Request body for inserting Todo")
@Data
public class InsertTodoRequest {

    //不传 默认值为0
    private Long parentId;

    private Long userId;

    @NotNull(message = "标题不能为空")
    private String title;

    private String description;

    @NotNull(message = "截止日期不能为空")
    private Date deadline;
}
