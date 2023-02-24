//package com.towelove.common.log.service;
//
//import com.towelove.common.core.constant.SecurityConstants;
//import com.towelove.system.api.RemoteLogService;
//import com.towelove.system.api.domain.SysOperLog;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
///**
// * 异步调用日志服务
// *
// * @author: 张锦标
// * @date: 2023/2/24 12:58
// */
//@Service
//public class AsyncLogService
//{
//    @Autowired
//    private RemoteLogService remoteLogService;
//
//    /**
//     * 保存系统日志记录
//     */
//    @Async
//    public void saveSysLog(SysOperLog sysOperLog)
//    {
//        remoteLogService.saveLog(sysOperLog, SecurityConstants.INNER);
//    }
//}
