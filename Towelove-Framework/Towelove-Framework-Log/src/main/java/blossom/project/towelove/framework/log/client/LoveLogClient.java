package blossom.project.towelove.framework.log.client;

import blossom.project.towelove.framework.log.mq.LoveLogProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * @author: 张锦标
 * @date: 2023/9/30 14:18
 * LogClient类
 */
@AutoConfiguration
public class LoveLogClient {
    @Autowired
    private LoveLogProducer loveLogProducer;

    public void info(String requestId,String logJson){
        //loveLogProducer.sendNormalLog(requestId,logJson);
    }

    public void error(String requestId,String logJson){
        //loveLogProducer.sendErrorLog(requestId,logJson);
    }
}
