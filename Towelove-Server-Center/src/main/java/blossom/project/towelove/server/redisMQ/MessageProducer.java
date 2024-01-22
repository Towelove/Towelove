//package blossom.project.towelove.server.redisMQ;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.connection.stream.RecordId;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author xiaobo
// */
//@Component
//@RequiredArgsConstructor
//public class MessageProducer {
//
//    private final RedisTemplate<String, String> redisTemplate;
//
//
//    public void sendMessage(String streamKey, String messageKey, String message) {
//        Map<String, String> messageMap = new HashMap<>();
//        messageMap.put(messageKey, message);
//
//        RecordId recordId = redisTemplate.opsForStream().add(streamKey, messageMap);
//        if (recordId != null) {
//            System.out.println("Message sent to Stream '" + streamKey + "' with RecordId: " + recordId);
//        }
//    }
//}
//
