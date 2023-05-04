package com.towelove.core.api;

import com.towelove.common.core.domain.R;
import com.towelove.core.api.factory.RemoteCoreFallbackFactory;
import com.towelove.core.api.model.vo.LoveAlbumCreateReqVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:季台星
 * @Date：2023-3-12 12:10
 */
@FeignClient(value = "towelove-core",fallbackFactory = RemoteCoreFallbackFactory.class,
        contextId = "RemoteCoreService")
public interface RemoteCoreService {

    @PostMapping("/core/loveAlbum/add")
    public R<Long> add(@RequestBody LoveAlbumCreateReqVO createReqVO);
}
