package blossom.project.towelove.loves.controller;

import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.service.TodolistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 下午4:52 30/11/2023
 */
@LoveLog
@RestController()
@RequestMapping("/loves/todo-list/v1")
public class TodoListController {

    @Autowired
    private TodolistService todolistService;
    @PostMapping("")
    public Result<Long> saveTodo(@RequestBody @Validated InsertTodoRequest insertTodoRequest){
        return Result.ok(todolistService.create(insertTodoRequest));
    }

    @PutMapping("")
    public Result<Long> updateTodo(@RequestBody @Validated UpdateTodoRequest updateTodoRequest){
        return Result.ok(todolistService.updateById(updateTodoRequest));
    }


}
