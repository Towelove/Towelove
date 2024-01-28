package blossom.project.towelove.loves.service;

import blossom.project.towelove.loves.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 留言服务接口
 */
public interface MessageService extends IService<Message> {
    /**
     * 通过伴侣ID获取所有相关留言
     *
     * @param coupleId 伴侣ID
     * @return 与该伴侣相关的所有留言列表
     */
    List<Message> getMessagesByCoupleId(Long coupleId);


    /**
     * 更新留言
     *
     * @param updateMessageRequest 留言更新请求
     * @return 更新后的留言详情
     */
    Message updateMessage(Message updateMessageRequest);

    /**
     * 通过ID删除留言
     *
     * @param messageId 留言ID
     * @return 删除操作的结果
     */
    Boolean deleteMessageById(Long messageId);

    /**
     * 创建留言
     *
     * @param createMessageRequest 留言创建请求
     * @return 创建的留言ID
     */
    Message createMessage(Message createMessageRequest);
}
