package blossom.project.towelove.framework.flower.enums;

import lombok.Data;

/**
 * @author: ZhangBlossom
 * @date: 2024/3/12 14:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 模版类型（场景）枚举类
 */
public enum TemplateTypeEnum {
    DEFAULT(0, "默认实现"),
    BIZ(1, "业务实现");

    private Integer code;
    private String name;

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    private TemplateTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
