package blossom.project.towelove.loves.controller;

import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.common.request.todoList.UpdateTodoRequest;
import blossom.project.towelove.common.request.todoList.UpdateWidget;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.todoList.TodoImagesResponse;
import blossom.project.towelove.common.response.todoList.TodoListCalendarResponse;
import blossom.project.towelove.common.response.todoList.TodoListResponse;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.service.TodoImagesService;
import blossom.project.towelove.loves.service.TodolistService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 待办事项
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 下午4:52 30/11/2023
 */
@LoveLog
@RestController
@AllArgsConstructor
@RequestMapping("/v1/loves/todo-list")
public class TodoListController {

    private TodolistService todolistService;
    private TodoImagesService todoImagesService;

    /**
     * 创建 待办事项
     *
     * @param insertTodoRequest
     * @return
     */
    @PostMapping("")
    public Result<Long> saveTodo(@RequestBody @Validated InsertTodoRequest insertTodoRequest) {
        return Result.ok(todolistService.create(insertTodoRequest));
    }

    /**
     * 更新 待办事项
     *
     * @param updateTodoRequest
     */
    @PutMapping("")
    public Result<Long> updateTodo(@RequestBody @Validated UpdateTodoRequest updateTodoRequest) {
        todolistService.updateById(updateTodoRequest);
        Optional.ofNullable(updateTodoRequest.getImages()).ifPresent(images -> todoImagesService.saveBatch(updateTodoRequest.getId(), images));
        return Result.ok();
    }

    /**
     * 删除 待办事项
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result<Long> deleteTodoById(@PathVariable("id") Long id) {
        todolistService.deleteById(id);
        return Result.ok();
    }

    /**
     * 获取 待办事项列表
     *
     * @param coupleId   用户id
     * @param parentId 父级id
     * @return
     */
    @GetMapping("")
    public Result<List<TodoListResponse>> getTodo(@RequestParam("coupleId") Long coupleId,
                                                  @RequestParam(name = "parentId", required = false, defaultValue = "0") Long parentId) {
        return Result.ok(todolistService.getList(coupleId, parentId));
    }

    /**
     * 获取 待办事项图片
     *
     * @param todoId 待办事项id
     * @return
     */
    @GetMapping("/images")
    public Result<List<TodoImagesResponse>> getTodoImagesById(@RequestParam("todoId") Long todoId) {
        return Result.ok(todoImagesService.getTodoImagesById(todoId));
    }

    /**
     * 获取 某个月 待办事项日历
     *
     * @param userId 用户id
     * @param date   日期 YYYY-MM
     */
    @GetMapping("/calendar")
    public Result<List<TodoListCalendarResponse>> getTodoCalendar(
            @RequestParam("userId") Long userId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM") Date date) {
        return Result.ok(todolistService.getTodoCalendar(userId, date));
    }

    /**
     * 更新小组件
     * @param updateWidget
     * @return
     */
    @PutMapping("/update-widget")
    public Result updateWidget(@RequestBody @Validated UpdateWidget updateWidget) {
        List<Long> res = todolistService.updateWidget(updateWidget);
        return Result.ok(res);
    }

    /**
     * 设定要提醒的
     * @param id
     * @param isFlag
     * @return
     */
    @PutMapping("/reminder/{id}")
    public Result<TodoListResponse> reminder(@PathVariable("id") Long id, @RequestParam("isFlag") Boolean isFlag) {
        return Result.ok(todolistService.reminder(id, isFlag));
    }


    /**
     * 获取小组件
     * @param coupleId
     * @return
     */
    @GetMapping("widget")
    public Result<List<TodoListResponse>> getWidget(@RequestParam("coupleId") Long coupleId) {
        return Result.ok(todolistService.widget(coupleId));
    }



}