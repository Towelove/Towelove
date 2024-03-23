package blossom.project.towelove.common.request.todoList;

import blossom.project.towelove.common.page.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 张锦标
 * @date: 2024/3/23 2:29 PM
 * TodoListPageRequest类
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TodoListPageRequest extends PageRequest {

    private Long pageSize = 7L;

}