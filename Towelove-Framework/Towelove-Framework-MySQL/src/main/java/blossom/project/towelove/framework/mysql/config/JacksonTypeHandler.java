package blossom.project.towelove.framework.mysql.config;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.Map;

@AutoConfiguration
public class JacksonTypeHandler extends AbstractJsonTypeHandler<Object> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected Object parse(String json) {
        try {
            return mapper.readValue(json, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("JSON 解析失败", e);
        }
    }

    @Override
    protected String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JSON 序列化失败", e);
        }
    }
}
