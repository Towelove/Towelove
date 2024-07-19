package blossom.project.towelove.treehole.service;

import blossom.project.towelove.treehole.config.ArkConfig;
import blossom.project.towelove.treehole.model.enums.ModelEnum;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ChatService类，负责处理聊天请求并与ArkService交互
 *
 * @author sujia
 * @date 2024/7/19
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ArkService arkService;
    private final ArkConfig arkConfig;

    /**
     * 开始聊天方法，发送用户消息并接收回复
     *
     * @param userMessage 用户发送的消息
     * @param modelEnum 模型枚举
     */
    @Async
    public void startChat(String userMessage, ModelEnum modelEnum) {
        String modelEndpoint = modelEnum.getEndpointId(arkConfig);
        ChatCompletionRequest chatRequest = createChatRequest(userMessage, modelEndpoint);

        try {
            arkService.streamChatCompletion(chatRequest)
                    .doOnError(Throwable::printStackTrace)
                    .blockingForEach(
                            choice -> {
                                if (choice.getChoices().size() > 0) {
                                    System.out.print(choice.getChoices().get(0).getMessage().getContent());
                                }
                            }
                    );
        } catch (Exception e) {
            log.error("Error while streaming chat completion: ", e);
            throw new RuntimeException(e);
        } finally {
            // shutdown service
            arkService.shutdownExecutor();
        }
    }

    /**
     * 创建聊天请求
     *
     * @param userMessage 用户发送的消息
     * @param modelEndpoint 模型端点
     * @return ChatCompletionRequest 对象
     */
    private ChatCompletionRequest createChatRequest(String userMessage, String modelEndpoint) {
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM)
                .content("你是豆包，一个由字节跳动开发的 AI 情感导师。你的任务是倾听用户的情感问题，提供支持和建议，并用温柔、理解和支持的语气进行沟通。")
                .build();
        final ChatMessage userChatMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(userMessage)
                .build();
        messages.add(systemMessage);
        messages.add(userChatMessage);

        return ChatCompletionRequest.builder()
                .model(modelEndpoint)
                .messages(messages)
                .build();
    }
}