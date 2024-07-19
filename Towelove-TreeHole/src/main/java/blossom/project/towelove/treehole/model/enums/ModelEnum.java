package blossom.project.towelove.treehole.model.enums;

import blossom.project.towelove.treehole.config.ArkConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.Map;

/**
 * 模型枚举类
 *
 * @author sujia
 * @date 2024/7/19
 */
@Slf4j
public enum ModelEnum {
    DOU_BAO_LITE_128K("doubaoLite128kEndpoint"),
    DOU_BAO_LITE_32K("doubaoLite32kEndpoint"),
    DOU_BAO_LITE_4K("doubaoLite4kEndpoint"),
    DOU_BAO_PRO_4K("doubaoPro4kEndpoint"),
    DOU_BAO_PRO_128K("doubaoPro128kEndpoint"),
    DOU_BAO_PRO_32K("doubaoPro32kEndpoint");

    private final String endpointFieldName;
    private static final Map<ModelEnum, Field> fieldCache = new EnumMap<>(ModelEnum.class);

    ModelEnum(String endpointFieldName) {
        this.endpointFieldName = endpointFieldName;
    }

    public String getEndpointId(ArkConfig arkConfig) {
        try {
            Field field = fieldCache.computeIfAbsent(this, k -> {
                try {
                    return ArkConfig.class.getDeclaredField(k.endpointFieldName);
                } catch (NoSuchFieldException e) {
                    throw new IllegalArgumentException("Unknown field: " + k.endpointFieldName, e);
                }
            });
            field.setAccessible(true);
            return (String) field.get(arkConfig);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to access field: " + endpointFieldName, e);
        }
    }
}