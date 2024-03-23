package blossom.project.towelove.loves.service.impl;


import blossom.project.towelove.loves.entity.Message;
import blossom.project.towelove.loves.mapper.MessageMapper;
import blossom.project.towelove.loves.service.MessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 苏佳
 * @Date 2024 01 17 18 37
 **/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Override
    public List<Message> getMessagesByCoupleId(Long coupleId) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("couple_id", coupleId).orderByAsc("create_time"); // 添加排序条件，按照 createTime 升序排列
        return this.list(queryWrapper);
    }

    @Override
    public Message updateMessage(Message updateMessageRequest) {
        // 更新留言信息
        this.updateById(updateMessageRequest);
        // 如果更新成功，返回更新后的留言详情
        return this.getById(updateMessageRequest.getId());
    }

    @Override
    public Boolean deleteMessageById(Long messageId) {
        // 删除留言
        return this.removeById(messageId);
    }

    @Override
    public Message createMessage(Message createMessageRequest) {
        // 创建留言
        boolean created = this.save(createMessageRequest);
        // 如果创建成功，返回新创建的留言的ID
        return this.getById(createMessageRequest.getId());
    }
}

