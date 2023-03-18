package com.towelove.msg.task.config;

import com.towelove.msg.task.domain.MailMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.cdi.Eager;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 张锦标
 * @date: 2023/3/18 13:26
 * TaskMapConfig类
 */

public class TaskMapUtil{

    public static final ConcurrentHashMap<String,MailMsg> taskMap
            = new ConcurrentHashMap<>();

    public static  ConcurrentHashMap<String,MailMsg> getTaskMap(){
        return taskMap;
    }
}
