package blossom.project.towelove.framework.flower.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 张锦标
 * @date: 2024/4/19 11:27 AM
 * FlowEnum类
 */

@Getter
@AllArgsConstructor
public enum FlowCodeEnum {

    // 顺风车匹配服务接口
    TOWELOVE_TEST_FLOW_EXECUTOR("towelove_test_flow_executor", "towelove测试executor")

    ;

    /**
     * 流程code，定义流程模板和启动流程时需要用
     */
    private String code;

    /**
     * 流程名
     */
    private String name;

    public static FlowCodeEnum getByCode(String code) {
        for (FlowCodeEnum e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }
}
