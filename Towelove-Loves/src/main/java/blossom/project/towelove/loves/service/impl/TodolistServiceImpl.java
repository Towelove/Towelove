package blossom.project.towelove.loves.service.impl;

import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.loves.convert.TodoListConvert;
import blossom.project.towelove.loves.entity.TodoList;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import blossom.project.towelove.loves.service.TodolistService;
import blossom.project.towelove.loves.mapper.TodolistMapper;
import org.springframework.stereotype.Service;

/**
* @author 29097
* @description 针对表【todolist】的数据库操作Service实现
* @createDate 2023-11-30 17:10:50
*/
@Service
public class TodolistServiceImpl extends ServiceImpl<TodolistMapper, TodoList>
    implements TodolistService{



    @Override
    public Long create(InsertTodoRequest insertTodoRequest) {
        TodoList  todoList = TodoListConvert.INSTANCE.convert(insertTodoRequest);
        this.save(todoList);
        return todoList.getId();
    }
}




