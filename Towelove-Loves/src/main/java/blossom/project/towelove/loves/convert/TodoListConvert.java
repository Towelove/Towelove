package blossom.project.towelove.loves.convert;

import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.common.request.todoList.UpdateTodoRequest;
import blossom.project.towelove.common.response.todoList.TodoListCalendarResponse;
import blossom.project.towelove.common.response.todoList.TodoListResponse;
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
     * @param insertTodoRequest
     * @return
     */
    TodoList convert(InsertTodoRequest insertTodoRequest);

    /**
     * 将UpdateTodoRequest转换为TodoList
     *
     * @param updateTodoRequest
     * @return
     */
    TodoList convert(UpdateTodoRequest updateTodoRequest);

    /**
     * 将TodoList转换为TodoListResponse
     *
     * @param todoList
     * @return
     */
    TodoListResponse convert(TodoList todoList);

    /**
     * 将TodoList转换为TodoListCalendarResponse
     *
     * @param todoLists
     * @return
     */
    List<TodoListCalendarResponse> convert(List<TodoList> todoLists);

    List<TodoListResponse> convertTodoListResponse(List<TodoList> todoLists);
}
