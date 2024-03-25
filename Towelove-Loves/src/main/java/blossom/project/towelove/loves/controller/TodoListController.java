package blossom.project.towelove.loves.controller;


import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.common.request.todoList.TodoListCreateRequest;
import blossom.project.towelove.common.request.todoList.TodoListUpdateRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.todoList.TodoListRespDTO;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.framework.user.core.UserInfoContextHolder;
import blossom.project.towelove.loves.service.TodoListService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @author: 张锦标
 * @date: 2024/3/23 10:09 AM
 * TodoListController类
 */

@LoveLog
@RestController
@RequestMapping("/todo")
public class TodoListController {

    @Autowired
    private TodoListService todolistService;

    /**
     * 创建
     *
     * @param createRequest
     * @return
     */
    @PostMapping("")
    public Result<TodoListRespDTO> create(@RequestBody @Validated TodoListCreateRequest createRequest) {
        return Result.ok(todolistService.create(createRequest));
    }


    /**
     * 按照ID查询相册详情信息
     *
     * @param todoId
     * @return
     */
    @GetMapping("")
    public Result<TodoListRespDTO> getTodoListDetailById(@Validated @RequestParam(name = "todoId")
                                                         @NotNull(message = "todoId Can not be null") Long todoId) {
        TodoListRespDTO result = todolistService.getTodoListDetailById(todoId);
        return Result.ok(result);
    }


    /**
     * 分页查询 获取情侣的小组件
     *
     * @return
     */
    @GetMapping("/page")
    public Result<List<TodoListRespDTO>> pageQuerytodo() {
        Long coupleId = UserInfoContextHolder.getCoupleId();
        if (Objects.isNull(coupleId)) {
            return Result.fail("coupleId is null", MDC.get(SecurityConstant.REQUEST_ID));
        }
        return Result.ok(todolistService.pageTodoList(coupleId));
    }

    /**
     * 基于ID修改
     *
     * @param updateRequest
     * @return
     */
    @PutMapping("")
    public Result<TodoListRespDTO> update(@Validated @RequestBody TodoListUpdateRequest updateRequest) {
        return Result.ok(todolistService.updateById(updateRequest));
    }

    /**
     * 基于ID删除
     *
     * @param todoId
     * @return
     */
    @DeleteMapping("")
    public Result<Boolean> deleteTodoListById(@RequestParam @Validated Long todoId) {
        return Result.ok(todolistService.deleteById(todoId));
    }

}
