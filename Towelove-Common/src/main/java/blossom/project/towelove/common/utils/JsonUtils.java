package blossom.project.towelove.common.utils;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**Json字符串与Java对象相互转换的工具类 */
public class JsonUtils {
    /**实例化ObjectMapper对象*/
    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
    	/**对JDK8.0的日期格式进行支持*/
    	JavaTimeModule javaTimeModule = new JavaTimeModule();
        //全局设置，对于未知的字段进行忽略。
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        MAPPER.registerModule(javaTimeModule);
    }
    /**
     * 将Java对象转换成json字符串。  
     * @param data 需要转换的Java对象
     * @return 符合json格式的字符串
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		} 
    }
    
    /**
     * 将json格式的字符串反序列化为Java对象
     * 
     * @param jsonData Json格式的字符串
     * @param  clazz 对象的类型
     * @return 反序列化的结果
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	throw new RuntimeException(e.getMessage());
        } 
    }
    
    /**
     * 将Json格式的字符串反序列化为对应的List集合  
     * @param jsonData 符合JSON格式的字符串
     * @param beanType  List集合中的泛型
     * @return List集合数据
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
     }

    /**
     * 实现将json格式的字符串转成Map集合
     */
    public static <K,V> Map<K,V> stringToMap(String jsonData, Class<K> keyType, Class<V> valueType){
        MapLikeType mapLikeType = MAPPER.getTypeFactory().constructMapLikeType(Map.class, keyType, valueType);

        Map<K,V> map = null;
        try {
            map = MAPPER.readValue(jsonData, mapLikeType);
            return map;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}

