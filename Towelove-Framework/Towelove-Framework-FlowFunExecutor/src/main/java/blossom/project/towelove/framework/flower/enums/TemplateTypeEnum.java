package blossom.project.towelove.framework.flower.enums;

import lombok.Data;

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
