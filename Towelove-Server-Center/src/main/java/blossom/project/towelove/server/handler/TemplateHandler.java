package blossom.project.towelove.server.handler;

import blossom.project.towelove.server.mq.rocketmq.TemplateDTO;
import org.springframework.stereotype.Component;

/**
 * @author: 张锦标
 * @date: 2024/4/18 5:25 PM
 * TemplateHandler类
 */
@Component
public class TemplateHandler {

    public String justTest(TemplateDTO templateDTO){

        return "test";
    }
}
