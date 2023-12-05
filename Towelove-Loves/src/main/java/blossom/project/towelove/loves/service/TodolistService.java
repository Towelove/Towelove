package blossom.project.towelove.loves.service;


import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.common.request.todoList.UpdateTodoRequest;
import blossom.project.towelove.common.response.todoList.TodoListCalendarResponse;
import blossom.project.towelove.common.response.todoList.TodoListResponse;
import blossom.project.towelove.loves.entity.TodoList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

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


    /**
     * 更新 待办事项
     *
     * @param updateTodoRequest
     */
    void updateById(UpdateTodoRequest updateTodoRequest);

    /**
     * 删除 待办事项
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 获取 待办事项列表
     *
     * @param userId   用户id
     * @param parentId 父级id
     * @return
     */
    List<TodoListResponse> getList(Long userId, Long parentId);

    /**
     * 获取 某个月 待办事项日历
     *
     * @param userId 用户id
     * @param date   日期
     * @return
     */
    List<TodoListCalendarResponse> getTodoCalendar(Long userId, Date date);

}
