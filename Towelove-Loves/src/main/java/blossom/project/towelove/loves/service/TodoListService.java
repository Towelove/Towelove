package blossom.project.towelove.loves.service;


import blossom.project.towelove.common.request.todoList.TodoListCreateRequest;
import blossom.project.towelove.common.request.todoList.TodoListUpdateRequest;
import blossom.project.towelove.common.response.todoList.TodoListRespDTO;
import blossom.project.towelove.loves.entity.TodoList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 29097
* @description 针对表【todolist】的数据库操作Service
* @createDate 2023-11-30 17:10:50
*/
public interface TodoListService extends IService<TodoList> {

    /**
     * 创建 待办事项
     * @param todoListCreateRequest
     * @return
     */
    TodoListRespDTO create(TodoListCreateRequest todoListCreateRequest);


    /**
     * 更新 待办事项
     *
     * @param todoListUpdateRequest
     */
    TodoListRespDTO updateById(TodoListUpdateRequest todoListUpdateRequest);

    /**
     * 删除 待办事项
     *
     * @param id
     */
    Boolean deleteById(Long id);

    /**
     * 获取 待办事项列表
     *
     * @param coupleId   用户id
     * @return
     */
    List<TodoListRespDTO> pageTodoList(Long coupleId);


    TodoListRespDTO getTodoListDetailById(Long todoId);
}
