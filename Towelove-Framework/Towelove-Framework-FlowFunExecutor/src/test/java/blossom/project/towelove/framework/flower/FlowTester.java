package blossom.project.towelove.framework.flower;

import blossom.project.towelove.framework.flower.entity.ToweloveAlbumContext;
import blossom.project.towelove.framework.flower.enums.FlowCodeEnum;
import blossom.project.towelove.framework.flower.executor.FlowExecutor;
import blossom.project.towelove.framework.flower.factory.ToweloveAlbumContextFactory;
import blossom.project.towelove.framework.flower.request.ToweloveAlbumRequest;

/**
 * @author: 张锦标
 * @date: 2024/4/19 11:22 AM
 * FlowTester类
 */
public class FlowTester {
    private FlowExecutor flowExecutor;
    public void test(ToweloveAlbumRequest request) {
        ToweloveAlbumContext context = ToweloveAlbumContextFactory.buildToweloveAlbumContext(request);
        // 1.创建流程执行器
        // 2.开启流程（执行原子组件）
        flowExecutor.executeFlow(FlowCodeEnum.TOWELOVE_TEST_FLOW_EXECUTOR.getCode(),
              context.getBizCode(),context.getBizTag(),context  );
    }
}
