package blossom.project.towelove.loves.service;


import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.loves.entity.TodoList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 29097
* @description 针对表【todolist】的数据库操作Service
* @createDate 2023-11-30 17:10:50
*/
public interface TodolistService extends IService<TodoList> {

    /**
     * 创建 待办事项
     * @param insertTodoRequest
     * @return
     */
    Long create(InsertTodoRequest insertTodoRequest);


}
