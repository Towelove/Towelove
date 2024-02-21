//package blossom.project.towelove.gateway.handler;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.web.reactive.result.view.ViewResolver;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * @description: 网关异常配置，覆盖默认的异常处理
// */
//@Configuration
//public class GlobalGatewayExceptionConfig {
//
//    @Primary
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
//                                                             ServerCodecConfigurer serverCodecConfigurer){
//        GlobalGatewayExceptionHandler globalGatewayExceptionHandler =new GlobalGatewayExceptionHandler();
//        globalGatewayExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
//        globalGatewayExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
//        globalGatewayExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
//        return globalGatewayExceptionHandler;
//    }
//}
