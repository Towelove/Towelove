//package blossom.project.towelove.server.redisMQ;
//
//import org.springframework.data.redis.connection.stream.ObjectRecord;
//import org.springframework.data.redis.stream.StreamListener;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
///**
// * @author xiaobo
// */
//@Component
//public class MessageConsumer implements StreamListener<String, ObjectRecord<String, String>> {
//
//    @Override
//    public void onMessage(ObjectRecord<String, String> message) {
//        String stream = message.getStream();
//        String messageId = message.getId().toString();
//        String messageBody = message.getValue();
//        System.out.println("Received message from Stream '" + stream + "' with messageId: " + messageId);
//        System.out.println("Message body: " + messageBody);
//    }
//}
//
