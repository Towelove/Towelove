package blossom.project.towelove.gateway.handler;

import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.common.response.Result;
import com.towelove.common.core.constant.HttpStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.gateway.handler
 * @className: ServiceExceptionHandler
 * @author: Link Ji
 * @description: GateWay网关的异常处理器，基于WebFlux
 * @date: 2023/11/23 14:28
 * @version: 1.0
 */
@Slf4j
@Getter
@Setter
@Component
public class GlobalGatewayExceptionHandler implements ErrorWebExceptionHandler {

//    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();
//    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
//    private List<ViewResolver> viewResolvers = Collections.emptyList();
//    private ThreadLocal<Result<?>> threadLocal=new ThreadLocal<>();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        log.error("网关异常全局处理，异常信息：{}",throwable.getMessage());
        //这里只是做个最简单的同一的异常结果输出，实际业务可根据throwable不同的异常处理不同的逻辑
        Result<Object> result = Result.fail(throwable.getMessage(), SecurityConstant.REQUEST_ID);
//        threadLocal.set(result);
//        exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.BAD_REQUEST);
//        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
//        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
//        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
//                .switchIfEmpty(Mono.error(throwable))
//                .flatMap((handler) -> handler.handle(newRequest))
//                .flatMap((response) -> write(exchange, response));
        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(result.toString().getBytes())));
    }

//    /**
//     * 统一返回指定异常信息(指定json格式输出)
//     * @param request
//     * @return
//     */
//    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request){
//        return ServerResponse.status(HttpStatus.ERROR)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(threadLocal.get()));
//    }

 	/**
     * 参考DefaultErrorWebExceptionHandler
     */
//    private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {
//        exchange.getResponse().getHeaders().setContentType(response.headers().getContentType());
//        return response.writeTo(exchange, new ResponseContext());
//    }

	 /**
     * 参考DefaultErrorWebExceptionHandler
     */
//    private class ResponseContext implements ServerResponse.Context {
//        private ResponseContext() {
//        }
//
//        @Override
//        public List<HttpMessageWriter<?>> messageWriters() {
//            return GlobalGatewayExceptionHandler.this.messageWriters;
//        }
//
//        @Override
//        public List<ViewResolver> viewResolvers() {
//            return GlobalGatewayExceptionHandler.this.viewResolvers;
//        }
//    }
}
