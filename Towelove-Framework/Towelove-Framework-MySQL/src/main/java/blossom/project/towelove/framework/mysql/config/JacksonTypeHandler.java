package blossom.project.towelove.framework.mysql.config;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TimeZone;

//@AutoConfiguration
public class JacksonTypeHandler extends AbstractJsonTypeHandler<Object> {
    /** 默认日期时间格式 */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 默认日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /** 默认时间格式 */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {

        //// 忽略json字符串中不识别的属性
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //// 忽略无法转换的对象
        //objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //// PrettyPrinter 格式化输出
        //objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //// NULL不参与序列化
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //
        //// 指定时区
        //objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        //// 日期类型字符串处理
        //objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));
        //
        //// java8日期日期处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        //javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        //javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        //javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        //javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        //javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));


        objectMapper.registerModule(javaTimeModule);

        objectMapper.registerModule(new JavaTimeModule());
    }
    @Override
    protected Object parse(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("JSON 解析失败", e);
        }
    }

    @Override
    protected String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
            //return objectMapper.writeValueAsString(obj).replace("\"", "");
        } catch (Exception e) {
            throw new RuntimeException("JSON 序列化失败", e);
        }
    }
}


