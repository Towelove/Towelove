package blossom.project.towelove.framework.flower.model;

import blossom.project.towelove.framework.flower.iface.ToweloveBizIface;
import blossom.project.towelove.framework.flower.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @author: ZhangBlossom
 * @date: 2024/1/23 19:20
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 * 遵循ddd，
 * 在这里实现底层的context类型
 * 可以理解为是towelove平台的业务上下层
 * 是一个很基础的对象
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ToweloveBizContext extends FlowBizContext
{

    /**
     * 业务角色，请求的发起者
     */
    protected Integer bizRole;

    /**
     * 操作人用户id
     */
    protected Long operateUserId;

    /**
     * 请求ID
     */
    protected String requestId;

    /**
     * 构造rpc入口的context
     * @param baseRequest
     * @param clazz clazz为null时，会使用当前系统默认的bizcontext对象，比如towelovebizcontext
     * @param <T>
     * @return
     */
    public static <T extends ToweloveBizContext> T buildRpcContext(BaseRequest baseRequest, Class<T> clazz) {

        return buildContext(baseRequest, clazz);
    }


    private static <T extends ToweloveBizContext> T buildContext(BaseRequest baseRequest, Class<T> clazz) {
        if (clazz == null) {
            clazz = (Class<T>) ToweloveBizContext.class;
        }

        T matchPlatformBizContext;
        try {
            matchPlatformBizContext = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("can not create instance of " + clazz.getName(), e);
        }
        matchPlatformBizContext.setBizCode(baseRequest.getBizCode());
        matchPlatformBizContext.setBizRole(baseRequest.getBizRole());
        return matchPlatformBizContext;
    }
}
