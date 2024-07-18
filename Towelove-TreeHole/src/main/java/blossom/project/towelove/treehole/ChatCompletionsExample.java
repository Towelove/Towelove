package blossom.project.towelove.treehole;

import blossom.project.towelove.treehole.config.ArkConfig;
import blossom.project.towelove.treehole.model.enums.ArkEnum;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 类的描述信息
 *
 * @author sujia
 * @date 2024/7/18
 */

public class ChatCompletionsExample {
    @Resource
    public static ArkService service;


    public static void main(String[] args) {
        System.out.println("\n----- streaming request -----");
        final List<ChatMessage> streamMessages = new ArrayList<>();
        final ChatMessage streamSystemMessage = ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM)
                .content("你是豆包，一个由字节跳动开发的 AI 情感导师。你的任务是倾听用户的情感问题，提供支持和建议，并用温柔、理解和支持的语气进行沟通。")
                .build();
        final ChatMessage streamUserMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content("你能给我一些如何更自信的建议吗？")
                .build();
        streamMessages.add(streamSystemMessage);
        streamMessages.add(streamUserMessage);

        ChatCompletionRequest streamChatCompletionRequest = ChatCompletionRequest.builder()
                .model(ArkEnum.INSTANCE.getEndpointId())
                .messages(streamMessages)
                .build();

        service.streamChatCompletion(streamChatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                .blockingForEach(
                        choice -> {
                            if (choice.getChoices().size() > 0) {
                                System.out.print(choice.getChoices().get(0).getMessage().getContent());
                            }
                        }
                );

        // shutdown service
        service.shutdownExecutor();
    }

}