package blossom.project.towelove.client.fallback;

import blossom.project.towelove.client.serivce.RemoteMsgService;
import blossom.project.towelove.client.serivce.RemoteUserService;
import blossom.project.towelove.common.constant.SecurityConstants;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.request.msg.MsgTaskPageRequest;
import blossom.project.towelove.common.response.Result;
import blossom.project.towelove.common.response.msg.MsgTaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Author:zhang.blossom
 * @Date：2023-11-22 14:10
 */
@AutoConfiguration
//使用下面这个注解必须保证该类的类路径被配置到
//META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
//@AutoConfiguration
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable throwable) {
        return new RemoteUserService() {

        };
    }
}
