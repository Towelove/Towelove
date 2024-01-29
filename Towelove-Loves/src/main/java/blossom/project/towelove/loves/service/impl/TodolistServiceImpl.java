package blossom.project.towelove.loves.service.Impl;

import blossom.project.towelove.client.serivce.msg.RemoteMsgTaskService;
import blossom.project.towelove.common.constant.Constant;
import blossom.project.towelove.common.exception.todo.ToDoErrorCode;
import blossom.project.towelove.common.exception.todo.TodoNotFoundException;
import blossom.project.towelove.common.exception.todo.TodoWidgetMaxException;
import blossom.project.towelove.common.request.msg.MsgTaskCreateRequest;
import blossom.project.towelove.common.request.todoList.InsertTodoRequest;
import blossom.project.towelove.common.request.todoList.UpdateTodoRequest;
import blossom.project.towelove.common.request.todoList.UpdateWidget;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 29097
 * @description 针对表【todolist】的数据库操作Service实现
 * @createDate 2023-11-30 17:10:50
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TodolistServiceImpl extends ServiceImpl<TodoListMapper, TodoList>
        implements TodolistService {


    /**
     * widget 最大数量
     */
    public static final int WIDGET_MAX = 2;
    public static final String STRING = "ToweLove任务提醒";

    private final TodoListMapper todoListMapper;

    private final TodoImagesMapper todoImagesMapper;

    private final RemoteMsgTaskService remoteMsgTaskService;


    @Override
    public Long create(InsertTodoRequest insertTodoRequest) {
        TodoList todoList = TodoListConvert.INSTANCE.convert(insertTodoRequest);
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
                .lt(TodoList::getDeadline,
                        Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .eq(TodoList::getParentId, 0); // 只查询顶级父级

        return TodoListConvert.INSTANCE.convert(todoListMapper.selectList(wrapper));
    }

    @Override
    public List<Long> updateWidget(UpdateWidget updateWidget) {
        List<Long> ids = updateWidget.getIds();
        LambdaQueryWrapper<TodoList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TodoList::getCoupleId, updateWidget.getCoupleId()).eq(TodoList::isWidget, Boolean.TRUE);
        List<TodoList> todoLists = todoListMapper.selectList(wrapper);
        if (todoLists.size() > WIDGET_MAX) {
            throw new TodoWidgetMaxException(ToDoErrorCode.WIDGET_MAX_CODE);
        }
        Map<Long, Boolean> todoIdsMap = todoLists.stream().collect(Collectors.toMap(TodoList::getId, todo -> false));
        if (CollectionUtil.isNotEmpty(updateWidget.getIds())) {
            ids.forEach(id -> todoIdsMap.put(id, true));
        }

        //通过value 进行对id分类
        List<Long> trueIds = todoIdsMap.keySet().stream().filter(todoIdsMap::get).toList();
        List<Long> falseIds = todoIdsMap.keySet().stream().filter(id -> !todoIdsMap.get(id)).toList();
        if (CollectionUtil.isNotEmpty(trueIds)) {
            todoListMapper.updateWidgetBatch(
                    true, trueIds);
        }
        if (CollectionUtil.isNotEmpty(falseIds)) {
            todoListMapper.updateWidgetBatch(false, falseIds);
        }
        return ids;
    }

    @Override
    public TodoListResponse reminder(Long id, Boolean isFlag) {
        TodoList todoList = todoListMapper.selectById(id);
        if (Objects.isNull(todoList)) {
            throw new TodoNotFoundException(ToDoErrorCode.NOT_FOUND_CODE);
        }
        if (todoList.ongoing()) {
            return TodoListConvert.INSTANCE.convert(todoList);
        }

        if (isFlag) {
            createMsgTask(todoList);
        } else {
            deleteMsgTask(todoList);
        }

        return TodoListConvert.INSTANCE.convert(todoList);

    }

    @Override
    public List<TodoListResponse> widget(Long coupleId) {
        return TodoListConvert.INSTANCE.convertTodoListResponse(todoListMapper.selectList(new LambdaQueryWrapper<TodoList>()
                .eq(TodoList::getCoupleId, coupleId)
                .eq(TodoList::isWidget, Boolean.TRUE).last("limit 2")));
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


    /**
     * 创建  提醒
     *
     * @param todoList
     * @return
     */
    private void createMsgTask(TodoList todoList) {
        if (todoList.isReminder() && todoList.getDeadline() == null) {
            return;
        }

        LocalDateTime sendDate = todoList.getDeadline().minusHours(1);
        MsgTaskCreateRequest request = new MsgTaskCreateRequest();
        request.setUserId(todoList.getCoupleId());
        //TODO 需要对接 查询mailAccount信息
//        request.setAccountId();
//        request.setReceiveAccount();
        request.setNickname(STRING);
        request.setTitle(todoList.getTitle());
        request.setContent("😭😭😭😭😭 待办列表还未完成 " + todoList.getTitle() + "   \n    " + todoList.getDescription());
        request.setSendDate(sendDate.toLocalDate());
        request.setSendTime(sendDate.toLocalTime());
        request.setMsgType(1);

        Result<MsgTaskResponse> msgTask = remoteMsgTaskService.createMsgTask(request);
        if (msgTask.getCode() != Constant.SUCCESS) {
            log.error("[待办 调用msgTask失败] code：{} msg: {}", msgTask.getCode(), msgTask.getMsg());
        }
        todoListMapper.update(todoList, new LambdaUpdateWrapper<TodoList>()
                .set(TodoList::getMsgTaskId, msgTask.getData().getId())
                .set(TodoList::isReminder, Boolean.TRUE)
                .eq(TodoList::getId, todoList.getId()));

    }


    private void deleteMsgTask(TodoList todoList) {
        if (!todoList.isReminder()) {
            return;
        }
        Result<Boolean> booleanResult =
                remoteMsgTaskService.batchDeleteMsgTask(CollectionUtil.newArrayList(todoList.getMsgTaskId()));
        if (!booleanResult.getData()) {
            log.error("[待办 调用msgTask失败] code：{} msg: {}", booleanResult.getCode(), booleanResult.getMsg());
        }
        todoListMapper.update(todoList, new LambdaUpdateWrapper<TodoList>()
                .set(TodoList::getMsgTaskId, null)
                .set(TodoList::isReminder, Boolean.FALSE)
                .eq(TodoList::getId, todoList.getId()));
    }


}




