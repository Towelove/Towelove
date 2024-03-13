package blossom.project.towelove.framework.flower.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;

/**
 * @author zhangblossom
 * rpc请求基类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseRequest {
    /**
     * 业务身份，原journeyType
     */
    private String bizCode;

    /**
     * 业务角色，请求的发起者
     */
    private Integer bizRole;

    /**
     * 场景ID
     */
    private Integer sceneId;

    /**
     * 订单来源的客户端类型
     */
    private String clientType;

    /**
     * 订单客户端的版本
     */
    private String clientVersion;

    /**
     * RPC调用来源
     */
    private Integer bizSource;

    /**
     * 是否命中实验
     * key：具体需求
     * value：命中实验组（true） 命中对照组（false）
     */
    private HashMap<String, Boolean> hitGrayMap;
}
