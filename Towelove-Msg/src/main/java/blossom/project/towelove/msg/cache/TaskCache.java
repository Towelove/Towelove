package blossom.project.towelove.msg.cache;

import blossom.project.towelove.msg.entity.CompletedMailMsgTask;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 张锦标
 * @date: 2023/3/18 13:26
 * TaskMapConfig类
 */

public class TaskCache {

    public static final ConcurrentHashMap<String, CompletedMailMsgTask> taskMap
            = new ConcurrentHashMap<>();

    public static  ConcurrentHashMap<String,CompletedMailMsgTask> getTaskMap(){
        return taskMap;
    }
}
