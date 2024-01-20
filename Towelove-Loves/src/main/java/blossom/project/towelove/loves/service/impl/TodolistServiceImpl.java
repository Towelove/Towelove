package blossom.project.towelove.loves.service.Impl;

import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.common.request.todoList.UpdateTodoRequest;
import blossom.project.towelove.common.request.todoList.UpdateWidget;
import blossom.project.towelove.common.response.todoList.TodoListCalendarResponse;
import blossom.project.towelove.common.response.todoList.TodoListResponse;
import blossom.project.towelove.loves.convert.TodoListConvert;
import blossom.project.towelove.loves.entity.TodoImages;
import blossom.project.towelove.loves.entity.TodoList;
import blossom.project.towelove.loves.mapper.TodoImagesMapper;
import blossom.project.towelove.loves.mapper.TodoListMapper;
import blossom.project.towelove.loves.service.TodolistService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 29097
* @description 针对表【todolist】的数据库操作Service实现
* @createDate 2023-11-30 17:10:50
*/
@Service
@RequiredArgsConstructor
public class TodolistServiceImpl extends ServiceImpl<TodoListMapper, TodoList>
    implements TodolistService{


    /**
     * widget 最大数量
     */
    public static final int WIDGET_MAX = 2;

    private final TodoListMapper todoListMapper;
    private final TodoImagesMapper todoImagesMapper;


    @Override
    public Long create(InsertTodoRequest insertTodoRequest) {
        TodoList  todoList = TodoListConvert.INSTANCE.convert(insertTodoRequest);
        this.save(todoList);
        return todoList.getId();
    }

    @Override
    public void updateById(UpdateTodoRequest updateTodoRequest) {
        TodoList todoList = TodoListConvert.INSTANCE.convert(updateTodoRequest);
        this.updateById(todoList);
    }

    @Override
    @Transactional
    public void deleteById(Long todoId) {
        Set<Long> idsToDelete = new HashSet<>();
        recursivelyCollectIds(todoId, idsToDelete);

        // 批量删除图片
        Optional.ofNullable(idsToDelete)
                .filter(CollectionUtil::isNotEmpty)
                .ifPresent(deleted -> {
                    todoImagesMapper.delete(new QueryWrapper<TodoImages>().in("todo_id", idsToDelete));
                });


        Optional.of(idsToDelete).filter(CollectionUtil::isNotEmpty).ifPresent(deleted -> {
            todoListMapper.delete(new QueryWrapper<TodoList>().in("id", idsToDelete));
        });
    }

    private void recursivelyCollectIds(Long todoId, Set<Long> idsToDelete) {
        idsToDelete.add(todoId);

        // 查询子任务 ID 并递归添加到集合中
        List<Long> childIds = todoListMapper.selectIdByPrentId(todoId);
        for (Long childId : childIds) {
            recursivelyCollectIds(childId, idsToDelete);
        }
    }

    @Override
    public List<TodoListResponse> getList(Long coupleId, Long parentId) {
        LambdaQueryWrapper<TodoList> queryWrapper = new LambdaQueryWrapper<>();
        Optional.ofNullable(coupleId).ifPresent(uid -> queryWrapper.eq(TodoList::getCoupleId, uid));
        Optional.ofNullable(parentId).ifPresent(pid -> queryWrapper.eq(TodoList::getParentId, pid));

        List<TodoList> todoLists = todoListMapper.selectList(queryWrapper);
        if (parentId != null && !parentId.equals(0L)) {
            return todoLists.stream().map(TodoListConvert.INSTANCE::convert).collect(Collectors.toList());
        }

        return todoLists.stream()
                .filter(this::isTopLevelParent)
                .map(todo -> buildTree(todo))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoListCalendarResponse> getTodoCalendar(Long userId, Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate startDate = localDate.withDayOfMonth(1);
        LocalDate endDate = localDate.withDayOfMonth(localDate.lengthOfMonth());

        LambdaQueryWrapper<TodoList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoList::getCoupleId, userId)
                .ge(TodoList::getDeadline, Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .lt(TodoList::getDeadline, Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .eq(TodoList::getParentId, 0); // 只查询顶级父级

        return TodoListConvert.INSTANCE.convert(todoListMapper.selectList(wrapper));
    }

    @Override
    public List<Long> updateWidget(UpdateWidget updateWidget) {
        LambdaQueryWrapper<TodoList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoList::getCoupleId, updateWidget.getCoupleId()).eq(TodoList::getWidget, Boolean.TRUE);
        List<TodoList> todoLists = todoListMapper.selectList(wrapper);
        if (todoLists.size() > WIDGET_MAX){
            throw new RuntimeException();
        }
        return null;
    }

    /**
     * 判断是否是顶级父级
     *
     * @param todo
     * @return
     */
    private boolean isTopLevelParent(TodoList todo) {
        return todo.getParentId() == null || todo.getParentId() == 0;
    }

    /**
     * 递归构建树
     *
     * @param todo
     * @return
     */
    private TodoListResponse buildTree(TodoList todo) {
        TodoListResponse response = TodoListConvert.INSTANCE.convert(todo);

        List<TodoListResponse> children = todoListMapper
                .selectList(new LambdaQueryWrapper<TodoList>()
                        .eq(TodoList::getParentId, todo.getId()))
                .stream()
                .map(this::buildTree)
                .collect(Collectors.toList());

        response.setChildren(children);
        return response;
    }


}




