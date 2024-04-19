package blossom.project.towelove.framework.flower.component;

import blossom.project.towelove.framework.flower.annotation.FlowService;
import blossom.project.towelove.framework.flower.entity.ToweloveAlbumContext;
import blossom.project.towelove.framework.flower.model.FlowBizContext;
import blossom.project.towelove.framework.flower.model.service.FlowSerivce;

import java.util.LinkedList;


/**
 * @author: ZhangBlossom
 * @date: 2024/3/13 19:38
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 还在开发ing
 */
@FlowService(name = "towelove流水线服务测试组件",
code = "towelove_flow_service_test_component",
desc = "towelove流水线服务测试组件")
public class ToweloveComponent implements FlowSerivce {

    /**
     * 具体要执行的流水线函数
     * 这里面要做的就是对流水线业务上下文进行处理
     *
     * @param flowBizContext
     */
    @Override
    public void execute(FlowBizContext flowBizContext) {

        ToweloveAlbumContext context = (ToweloveAlbumContext)flowBizContext;
        //tood 这里的xxx，其实就是因为这是ddd系统，所以context其实本身可以实现一些接口
        //而这里的xxx就是这些接口的实现
        //todo 这里调用xxxx方法，比如某个下游方法，从而得到albums的信息
        System.out.println("flowexecutor 执行成功。。。");
        //这里一般会有一个requestBuilder来构建一个request对象
        //ToweloveRequestAlbum request = xxxbuilder。builderalbumsrequest（）；
        //List<AlbumEntity> albums = xxxService.xxx（request）方法

        //这里一般就是把albums放进去 然后作为流水线处理的结果
        context.setResult(new LinkedList<>());
    }

}
