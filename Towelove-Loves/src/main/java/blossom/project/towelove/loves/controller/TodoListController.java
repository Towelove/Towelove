package blossom.project.towelove.loves.controller;

import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.common.request.todoList.UpdateTodoRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.todoList.TodoImagesResponse;
import blossom.project.towelove.common.response.todoList.TodoListResponse;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.service.TodoImagesService;
import blossom.project.towelove.loves.service.TodolistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 下午4:52 30/11/2023
 */
@LoveLog
@RestController()
@RequestMapping("/v1/loves/todo-list")
public class TodoListController {

    @Autowired
    private TodolistService todolistService;
    @Autowired
    private TodoImagesService todoImagesService;

    @PostMapping("")
    public Result<Long> saveTodo(@RequestBody @Validated InsertTodoRequest insertTodoRequest) {
        return Result.ok(todolistService.create(insertTodoRequest));
    }

    @PutMapping("")
    public Result<Long> updateTodo(@RequestBody @Validated UpdateTodoRequest updateTodoRequest) {
        todolistService.updateById(updateTodoRequest);
        Optional.ofNullable(updateTodoRequest.getImages()).ifPresent(images -> todoImagesService.saveBatch(updateTodoRequest.getId(), images));
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Long> deleteTodoById(@PathVariable("id") Long id) {
        todolistService.deleteById(id);
        return Result.ok();
    }

    @GetMapping("")
    public Result<List<TodoListResponse>> getTodo(@RequestParam("userId") Long userId,
                                                  @RequestParam(name = "parentId", required = false, defaultValue = "0") Long parentId) {
        return Result.ok(todolistService.getList(userId, parentId));
    }

    @GetMapping("/images")
    public Result<List<TodoImagesResponse>> getTodoImagesById(@RequestParam("todoId") Long todoId) {
        return Result.ok(todoImagesService.getTodoImagesById(todoId));
    }

}