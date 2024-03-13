package blossom.project.towelove.framework.flower.component;

import blossom.project.towelove.framework.flower.annotation.FlowService;
import blossom.project.towelove.framework.flower.entity.ToweloveAlbumShowContext;
import blossom.project.towelove.framework.flower.model.FlowBizContext;
import blossom.project.towelove.framework.flower.model.service.FlowSerivce;

import java.util.LinkedList;

/**
 *
 */
@FlowService(name = "towelove组件")
public class ToweloveComponent implements FlowSerivce {

    @Override
    public void execute(FlowBizContext flowBizContext) {

        ToweloveAlbumShowContext context = (ToweloveAlbumShowContext)flowBizContext;
        //tood 这里的xxx，其实就是因为这是ddd系统，所以context其实本身可以实现一些接口
        //而这里的xxx就是这些接口的实现
        //todo 这里调用xxxx方法，比如某个下游方法，从而得到albums的信息

        //这里一般会有一个requestBuilder来构建一个request对象
        //ToweloveRequestAlbum request = xxxbuilder。builderalbumsrequest（）；
        //List<AlbumEntity> albums = xxxService.xxx（request）方法

        //这里一般就是把albums放进去 然后作为流水线处理的结果
        context.setResult(new LinkedList<>());
    }

}
