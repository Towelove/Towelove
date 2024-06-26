package blossom.project.towelove.loves.service.impl;

import blossom.project.towelove.client.serivce.msg.RemoteMsgTaskService;
import blossom.project.towelove.common.exception.RemoteException;
import blossom.project.towelove.common.exception.ServerException;
import blossom.project.towelove.common.request.msg.MsgTaskCreateRequest;
import blossom.project.towelove.common.request.todoList.TodoListCreateRequest;
import blossom.project.towelove.common.request.todoList.TodoListUpdateRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import blossom.project.towelove.common.response.todoList.TodoListRespDTO;
import blossom.project.towelove.common.utils.StringUtils;
import blossom.project.towelove.framework.user.core.UserInfoContextHolder;
import blossom.project.towelove.loves.convert.TodoListConvert;
import blossom.project.towelove.loves.entity.TodoList;
import blossom.project.towelove.loves.mapper.TodoListMapper;
import blossom.project.towelove.loves.service.TodoListService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static blossom.project.towelove.common.exception.errorcode.BaseErrorCode.*;

/**
 * @author: 张锦标
 * @date: 2024/3/23 13:06
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TodoListServiceImpl extends ServiceImpl<TodoListMapper, TodoList>
        implements TodoListService {

    /**
     * 是否开启消息推送
     */
    @Value("${open.msg.task:false}")
    private boolean openMsgTask;
    /**
     * widget 最大数量
     */
    public static final int WIDGET_MAX = 2;

    private final TodoListMapper todoListMapper;

    private final RemoteMsgTaskService remoteMsgTaskService;


    @Override
    @Transactional
    public TodoListRespDTO create(TodoListCreateRequest todoListCreateRequest) {
        Long coupleId = UserInfoContextHolder.getCoupleId();
        if (Objects.isNull(coupleId)) {
            throw new ServerException(COUPLEID_EMPTY_ERROR.message(), null, COUPLEID_EMPTY_ERROR);
        }
        TodoList todoList = TodoListConvert.INSTANCE.convert(todoListCreateRequest);
        if (openMsgTask && todoList.isReminder()) {
            String email = UserInfoContextHolder.getEmail();
            if (StringUtils.isBlank(email)) {
                throw new ServerException(EMAIL_EMPTY_ERROR.message(), null, EMAIL_EMPTY_ERROR);
            }
            MsgTaskCreateRequest msgTaskCreateRequest = MsgTaskCreateRequest.builder()
                    .content("你设定的待办事项还未执行，别忘记了哦～")
                    .nickname("Towelove待办事项提醒")
                    .receiveAccount(email)
                    .sendDate(todoList.getDeadline().toLocalDate())
                    .sendTime(todoList.getDeadline().toLocalTime().plusHours(-3))
                    .build();
            try {
                Result<MsgTaskResponse> msgTask = remoteMsgTaskService.createMsgTask(msgTaskCreateRequest);
                //远程调用基本判断逻辑
                if (Objects.isNull(msgTask) && msgTask.isError(msgTask)) {
                    throw new RemoteException("远程调用创建定时提醒任务失败", null, REMOTE_ERROR);
                }
                todoList.setMsgTaskId(msgTask.getData().getId());
            } catch (RemoteException e) {
                throw new RemoteException("远程调用创建定时提醒任务失败", e, REMOTE_ERROR);
            } catch (Exception e) {
                throw new RuntimeException("创建待办列表失败", e);
            }
        }
        try {
            todoList.setStatus(0);
            todoList.setCoupleId(coupleId);
            this.save(todoList);
        } catch (Exception e) {
            throw new RuntimeException("创建待办列表失败", e);
        }
        TodoListRespDTO todoListRespDTO = TodoListConvert.INSTANCE.convert(todoList);
        return todoListRespDTO;
    }


    @Override
    @Transactional
    public TodoListRespDTO updateById(TodoListUpdateRequest todoListUpdateRequest) {
        Long coupleId = UserInfoContextHolder.getCoupleId();
        if (Objects.isNull(coupleId)) {
            throw new ServerException(COUPLEID_EMPTY_ERROR.message(), null, COUPLEID_EMPTY_ERROR);
        }
        TodoList todoList = TodoListConvert.INSTANCE.convert(todoListUpdateRequest);
        todoList.setCoupleId(coupleId);
        todoList.setStatus(todoListUpdateRequest.getStatus());
        //判断是否提醒
        if (openMsgTask && todoList.isReminder()) {
            TodoList dbToDoList = this.getById(todoList.getId());
            String email = UserInfoContextHolder.getEmail();
            if (StringUtils.isBlank(email)) {
                throw new ServerException(EMAIL_EMPTY_ERROR.message(), null, EMAIL_EMPTY_ERROR);
            }
            MsgTaskCreateRequest msgTaskCreateRequest = MsgTaskCreateRequest.builder()
                    .content("你设定的待办事项还未执行，别忘记了哦～")
                    .nickname("Towelove待办事项提醒")
                    .receiveAccount(email)
                    .sendDate(todoList.getDeadline().toLocalDate())
                    .sendTime(todoList.getDeadline().toLocalTime().plusHours(-3))
                    .build();
            try {
                Result<MsgTaskResponse> msgTask = remoteMsgTaskService.createMsgTask(msgTaskCreateRequest);
                //远程调用基本判断逻辑
                if (Objects.isNull(msgTask) && msgTask.isError(msgTask)) {
                    throw new RemoteException("远程调用创建定时提醒任务失败", null, REMOTE_ERROR);
                }
                todoList.setMsgTaskId(msgTask.getData().getId());
                //删除原有的提醒信息
                if (Objects.nonNull(dbToDoList.getMsgTaskId())) {
                    remoteMsgTaskService.batchDeleteMsgTask(
                            Collections.singletonList(dbToDoList.getMsgTaskId()));
                }
            } catch (RemoteException e) {
                throw new RemoteException("远程调用定时提醒任务失败", e, REMOTE_ERROR);
            } catch (Exception e) {
                throw new RuntimeException("其他异常。。。", e);
            }
        }
        try {
            boolean success = this.updateById(todoList);
            if (!success){
                throw new RuntimeException("更新数据失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("更新数据失败", e);
        }
        TodoListRespDTO todoListRespDTO = TodoListConvert.INSTANCE.convert(todoList);
        return todoListRespDTO;
    }

    @Override
    @Transactional
    public Boolean deleteById(Long todoId) {
        TodoList dbToDoList = this.getById(todoId);
        if (Objects.isNull(dbToDoList)) {
            throw new ServerException("待办不存在，对应id为：" + todoId, null, ENTITY_NOT_FOUND);
        }
        //判断是否提醒
        if (openMsgTask && dbToDoList.isReminder()) {
            try {
                //删除原有的提醒信息
                remoteMsgTaskService.batchDeleteMsgTask(
                        Collections.singletonList(dbToDoList.getMsgTaskId()));
            } catch (RemoteException e) {
                throw new RemoteException("远程调用定时提醒任务失败", e, REMOTE_ERROR);
            } catch (Exception e) {
                throw new RuntimeException("其他异常。。。", e);
            }
        }
        try {
            baseMapper.deleteById(todoId);
        } catch (Exception e) {
            throw new RuntimeException("删除数据失败", e);
        }
        return Boolean.TRUE;
    }

    /**
     * 列表分页查询
     *
     * @param coupleId 用户id
     * @return
     */
    @Override
    public List<TodoListRespDTO> pageTodoList(Long coupleId) {
        //返回所有未被删除的数据
        List<TodoList> todoLists = todoListMapper.selectAllByCoupleId(coupleId);
        if (CollectionUtil.isEmpty(todoLists)) {
            return Collections.emptyList();
        }
        List<TodoListRespDTO> todoListRespDTOS = TodoListConvert.INSTANCE.convertTodoListResponse(todoLists);
        return todoListRespDTOS;
    }

    /**
     * 获取详细信息
     *
     * @param todoId
     * @return
     */
    @Override
    public TodoListRespDTO getTodoListDetailById(Long todoId) {
        TodoList todoList = this.getById(todoId);
        if (Objects.nonNull(todoList)) {
            return TodoListConvert.INSTANCE.convert(todoList);
        }
        return null;
    }

}