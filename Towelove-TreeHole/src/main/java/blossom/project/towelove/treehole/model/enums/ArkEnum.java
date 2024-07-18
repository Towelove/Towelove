package blossom.project.towelove.treehole.model.enums;

import lombok.Data;
import lombok.Getter;

/**
 * 类的描述信息
 *
 * @author sujia
 * @date 2024/7/18
 */

public enum ArkEnum {
    INSTANCE;

    @Getter
    private String apiKey;
    @Getter
    private String endpointId;

    public void init(String apiKey, String endpointId) {
        this.apiKey = apiKey;
        this.endpointId = endpointId;
    }

}
