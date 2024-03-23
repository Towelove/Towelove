package blossom.project.towelove.loves.convert;

import blossom.project.towelove.common.request.todoList.TodoListCreateRequest;
import blossom.project.towelove.common.request.todoList.TodoListUpdateRequest;
import blossom.project.towelove.common.response.todoList.TodoListRespDTO;
import blossom.project.towelove.loves.entity.TodoList;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 上午10:14 1/12/2023
 */
@Mapper
public interface TodoListConvert {
    TodoListConvert INSTANCE = Mappers.getMapper(TodoListConvert.class);

    /**
     * 将InsertTodoRequest转换为TodoList
     *
     * @param todoListCreateRequest
     * @return
     */
    TodoList convert(TodoListCreateRequest todoListCreateRequest);

    /**
     * 将UpdateTodoRequest转换为TodoList
     *
     * @param todoListUpdateRequest
     * @return
     */
    TodoList convert(TodoListUpdateRequest todoListUpdateRequest);

    /**
     * 将TodoList转换为TodoListResponse
     *
     * @param todoList
     * @return
     */
    TodoListRespDTO convert(TodoList todoList);


    List<TodoListRespDTO> convertTodoListResponse(List<TodoList> todoLists);
}
