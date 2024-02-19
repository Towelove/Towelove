package blossom.project.towelove.common.request.todoList;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

//@ApiModel(description = "Request body for inserting Todo")
@Data
public class InsertTodoRequest {

    //不传 默认值为0
    private Long parentId;

    private Long coupleId;

    @NotNull(message = "标题不能为空")
    private String title;

    private String description;

    @NotNull(message = "截止日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;


}
