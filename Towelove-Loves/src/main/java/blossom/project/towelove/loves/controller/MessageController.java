package blossom.project.towelove.loves.controller;

import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.framework.log.annotation.LoveLog;
import blossom.project.towelove.loves.entity.Message;
import blossom.project.towelove.loves.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 苏佳
 * @Date 2024 01 17 18 41
 *
 * 功能:留言板的增删改查
 **/
@LoveLog
@RestController
@RequestMapping("/v1/love/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;




    // 获取特定伴侣的所有留言
    @GetMapping("/couple/{coupleId}")
    public Result<List<Message>> getMessagesByCoupleId(@PathVariable Long coupleId) {
        return Result.ok(messageService.getMessagesByCoupleId(coupleId));
    }

    // 创建新留言
    @PostMapping
    public Result<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return Result.ok(createdMessage);
    }

    // 更新留言
    @PutMapping("/{messageId}")
    public Result<Message> updateMessage(@PathVariable Long messageId, @RequestBody Message message) {
        message.setId(messageId);
        return Result.ok(messageService.updateMessage(message));
    }

    // 删除留言
    @DeleteMapping("/{messageId}")
    public Result<Boolean> deleteMessage(@PathVariable Long messageId) {
        return Result.ok(messageService.deleteMessageById(messageId));
    }
}
