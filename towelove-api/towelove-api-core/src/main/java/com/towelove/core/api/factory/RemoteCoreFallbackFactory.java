package com.towelove.core.api.factory;

import com.towelove.common.core.domain.R;
import com.towelove.core.api.RemoteCoreService;
import com.towelove.core.api.model.vo.LoveAlbumCreateReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:张锦标
 * @Date：2023-5-3 12:10
 */
@Component
//使用下面这个注解必须保证该类的类路径被配置到
//META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
@AutoConfiguration
public class RemoteCoreFallbackFactory implements FallbackFactory<RemoteCoreService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteCoreFallbackFactory.class);

    @Override
    public RemoteCoreService create(Throwable throwable) {
        return new RemoteCoreService() {
            @Override
            public R<Long> add(LoveAlbumCreateReqVO createReqVO) {
                return R.fail("新增恋爱相册失败");
            }
        };
    }
}
