package blossom.project.towelove.msg.service.Impl;

import blossom.project.towelove.common.annotaion.Transaction;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.msg.convert.MsgTaskConvert;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.msg.entity.MsgTask;
import blossom.project.towelove.msg.mapper.MsgTaskMapper;
import blossom.project.towelove.msg.service.MsgTaskService;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import blossom.project.towelove.common.request.msg.MsgTaskCreateRequest;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.request.msg.MsgTaskUpdateRequest;
import java.util.List;
import java.util.Objects;


/**
 * (MsgTask) 表服务实现类
 *
 * @author 张锦标
 * @since 2023-11-21 19:33:06
 *
 * 无需考虑异常处理，LoveLog已经完全将异常都处理完毕了
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MsgTaskServiceImpl extends ServiceImpl<MsgTaskMapper, MsgTask> implements MsgTaskService {

    private final MsgTaskMapper msgTaskMapper;


    @Override
    public MsgTaskResponse getMsgTaskById(Long msgTaskId) {
        MsgTask msgTask = msgTaskMapper.selectById(msgTaskId);
        if (Objects.isNull(msgTask)) {
            //数据为空
            log.info("the msgtask corresponding to the msgtaskid is empty");
            return null;
        }
        MsgTaskResponse response = MsgTaskConvert.INSTANCE.convert(msgTask);
        return response;
    }

    @Override
    public PageResponse pageQueryMsgTask(MsgTaskPageRequest requestParam) {
        log.info("the pageQuery info is :{} ",JSON.toJSONString(requestParam));
        LambdaQueryWrapper<MsgTask> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MsgTask::getUserId, requestParam.getUserId());
        lqw.eq(MsgTask::getDeleted, 0);
        lqw.last("limit " + (requestParam.getPageNo()-1) * requestParam.getPageSize()
                + "," + requestParam.getPageSize());
        List<MsgTask> msgTasks = msgTaskMapper.selectList(lqw);
        return new PageResponse(requestParam.getPageNo(), requestParam.getPageSize(), msgTasks);
    }

    @Override
    @Transaction
    public MsgTaskResponse updateMsgTask(MsgTaskUpdateRequest updateRequest) {
        if (Objects.isNull(updateRequest)){
            return null;
        }
        MsgTask msgTask = MsgTaskConvert.INSTANCE.convert(updateRequest);
        if (Objects.isNull(msgTask)){
            return null;
        }
        //先删除在创建
        msgTaskMapper.deleteById(msgTask.getId());
        msgTask.setId(null);
        msgTaskMapper.insert(msgTask);
        msgTask = msgTaskMapper.selectById(msgTask.getId());
        MsgTaskResponse response = MsgTaskConvert.INSTANCE.convert(msgTask);
        return response;
    }

    @Override
    public Boolean deleteMsgTaskById(Long msgTaskId) {
        if (Objects.isNull(msgTaskId)){
            return false;
        }
        LambdaQueryWrapper<MsgTask> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MsgTask::getId,msgTaskId );
        lqw.eq(MsgTask::getDeleted, 0);
        MsgTask msgTask = msgTaskMapper.selectOne(lqw);
        if (Objects.isNull(msgTask)){
            return true;
        }
        int delete = msgTaskMapper.delete(lqw);
        return delete>0;
    }

    @Override
    public Boolean batchDeleteMsgTask(List<Long> ids) {
        if(CollectionUtil.isEmpty(ids)){
            return false;
        }
        int batchIds = msgTaskMapper.deleteBatchIds(ids);
        return batchIds==ids.size();
    }


    @Override
    @Transaction
    public Result createMsgTask(MsgTaskCreateRequest createRequest) {
        log.info("the request info is: {} ", JSON.toJSONString(createRequest));
        MsgTask msgTask = MsgTaskConvert.INSTANCE.convert(createRequest);
        msgTaskMapper.insert(msgTask);
        return Result.ok(msgTask);
    }

}

