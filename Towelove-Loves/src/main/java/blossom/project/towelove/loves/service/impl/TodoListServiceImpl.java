package blossom.project.towelove.loves.service.impl;

import blossom.project.towelove.client.serivce.msg.RemoteMsgTaskService;
import blossom.project.towelove.common.constant.Constant;
import blossom.project.towelove.common.constant.RedisKeyConstant;
import blossom.project.towelove.common.constant.SecurityConstant;
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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static blossom.project.towelove.common.exception.errorcode.BaseErrorCode.EMAIL_EMPTY_ERROR;
import static blossom.project.towelove.common.exception.errorcode.BaseErrorCode.REMOTE_ERROR;

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
     * widget 最大数量
     */
    public static final int WIDGET_MAX = 2;

    private final TodoListMapper todoListMapper;

    private final RemoteMsgTaskService remoteMsgTaskService;


    @Override
    @Transactional
    public TodoListRespDTO create(TodoListCreateRequest todoListCreateRequest) {
        TodoList todoList = TodoListConvert.INSTANCE.convert(todoListCreateRequest);
        if (todoList.isReminder()) {
            String email = UserInfoContextHolder.getEmail();
            if (StringUtils.isBlank(email)) {
                throw new ServerException(EMAIL_EMPTY_ERROR.message(),null, EMAIL_EMPTY_ERROR);
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
                this.save(todoList);
            } catch (RemoteException e) {
                throw new RemoteException("远程调用创建定时提醒任务失败", e, REMOTE_ERROR);
            }catch (Exception e){
                throw new RuntimeException("创建待办列表失败",e);
            }
        }
        TodoListRespDTO todoListRespDTO = TodoListConvert.INSTANCE.convert(todoList);
        return todoListRespDTO;
    }


    @Override
    @Transactional
    public TodoListRespDTO updateById(TodoListUpdateRequest todoListUpdateRequest) {
        TodoList todoList = TodoListConvert.INSTANCE.convert(todoListUpdateRequest);
        this.updateById(todoList);
        return TodoListConvert.INSTANCE.convert(todoList);
    }

    @Override
    @Transactional
    public Boolean deleteById(Long todoId) {
        return this.deleteById(todoId);
    }

    @Override
    public List<TodoListRespDTO> pageTodoList(Long coupleId) {
        return null;
    }

    @Override
    public TodoListRespDTO getTodoListDetailById(Long todoId) {
        return null;
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
        request.setNickname(RedisKeyConstant.REMIND_SUBJECT);
        request.setTitle(todoList.getTitle());
        request.setContent("😭😭😭😭😭 待办列表还未完成 " + todoList.getTitle() + "   \n    " + todoList.getDescription());
        request.setSendDate(sendDate.toLocalDate());
        request.setSendTime(sendDate.toLocalTime());
        //type=0表示只发送一次
        request.setMsgType(0);
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