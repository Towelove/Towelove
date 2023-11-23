//package blossom.project.towelove.gateway.handler;
//
//import blossom.project.towelove.common.constant.SecurityConstants;
//import blossom.project.towelove.common.response.Result;
//import com.alibaba.fastjson.JSON;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
//import org.springframework.boot.web.reactive.error.ErrorAttributes;
//import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
//import org.springframework.core.annotation.MergedAnnotation;
//import org.springframework.core.annotation.MergedAnnotations;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebHandler;
//import org.springframework.web.server.handler.WebHandlerDecorator;
//import reactor.core.publisher.Mono;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Optional;
//
///**
// * @projectName: Towelove
// * @package: blossom.project.towelove.gateway.handler
// * @className: ServiceExceptionHandler
// * @author: Link Ji
// * @description: GateWay网关的异常处理器，基于WebFlux
// * @date: 2023/11/23 14:28
// * @version: 1.0
// */
//@Component
//@Slf4j
//public class ServiceExceptionHandler implements ErrorAttributes {
//    private static final String ERROR_INTERNAL_ATTRIBUTE = DefaultErrorAttributes.class.getName() + ".ERROR";
//
//    public ServiceExceptionHandler() {
//    }
//
//    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
//        Map<String, Object> errorAttributes = this.getErrorAttributes(request, options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE));
//        if (!options.isIncluded(ErrorAttributeOptions.Include.EXCEPTION)) {
//            errorAttributes.remove("exception");
//        }
//
//        if (!options.isIncluded(ErrorAttributeOptions.Include.STACK_TRACE)) {
//            errorAttributes.remove("trace");
//        }
//
//        if (!options.isIncluded(ErrorAttributeOptions.Include.MESSAGE) && errorAttributes.get("message") != null) {
//            errorAttributes.remove("message");
//        }
//
//        if (!options.isIncluded(ErrorAttributeOptions.Include.BINDING_ERRORS)) {
//            errorAttributes.remove("errors");
//        }
//
//        return errorAttributes;
//    }
//
//    private Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
//        Map<String, Object> errorAttributes = new LinkedHashMap();
//        errorAttributes.put("path", request.path());
//        Throwable error = this.getError(request);
//        MergedAnnotation<ResponseStatus> responseStatusAnnotation = MergedAnnotations.from(error.getClass(), MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(ResponseStatus.class);
//        HttpStatus errorStatus = this.determineHttpStatus(error, responseStatusAnnotation);
//        errorAttributes.put("status", errorStatus.value());
//        errorAttributes.put("error", errorStatus.getReasonPhrase());
//        errorAttributes.put("message", this.determineMessage(error, responseStatusAnnotation));
//        errorAttributes.put("requestId", request.exchange().getRequest().getId());
//        this.handleException(errorAttributes, this.determineException(error), includeStackTrace);
//        return errorAttributes;
//    }
//
//    private HttpStatus determineHttpStatus(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation) {
//        return error instanceof ResponseStatusException ? ((ResponseStatusException)error).getStatus() : (HttpStatus)responseStatusAnnotation.getValue("code", HttpStatus.class).orElse(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    private String determineMessage(Throwable error, MergedAnnotation<ResponseStatus> responseStatusAnnotation) {
//        if (error instanceof BindingResult) {
//            return error.getMessage();
//        } else if (error instanceof ResponseStatusException) {
//            return ((ResponseStatusException)error).getReason();
//        } else {
//            String reason = (String)responseStatusAnnotation.getValue("reason", String.class).orElse("");
//            if (StringUtils.hasText(reason)) {
//                return reason;
//            } else {
//                return error.getMessage() != null ? error.getMessage() : "";
//            }
//        }
//    }
//
//    private Throwable determineException(Throwable error) {
//        if (error instanceof ResponseStatusException) {
//            return error.getCause() != null ? error.getCause() : error;
//        } else {
//            return error;
//        }
//    }
//
//    private void addStackTrace(Map<String, Object> errorAttributes, Throwable error) {
//        StringWriter stackTrace = new StringWriter();
//        error.printStackTrace(new PrintWriter(stackTrace));
//        stackTrace.flush();
//        errorAttributes.put("trace", stackTrace.toString());
//    }
//
//    private void handleException(Map<String, Object> errorAttributes, Throwable error, boolean includeStackTrace) {
//        errorAttributes.put("exception", error.getClass().getName());
//        if (includeStackTrace) {
//            this.addStackTrace(errorAttributes, error);
//        }
//
//        if (error instanceof BindingResult) {
//            BindingResult result = (BindingResult)error;
//            if (result.hasErrors()) {
//                errorAttributes.put("errors", result.getAllErrors());
//            }
//        }
//
//    }
//
//    public Throwable getError(ServerRequest request) {
//        Optional<Object> error = request.attribute(ERROR_INTERNAL_ATTRIBUTE);
//        error.ifPresent((value) -> {
//            request.attributes().putIfAbsent(ErrorAttributes.ERROR_ATTRIBUTE, value);
//        });
//        return (Throwable)error.orElseThrow(() -> {
//            return new IllegalStateException("Missing exception attribute in ServerWebExchange");
//        });
//    }
//
//    public void storeErrorInformation(Throwable error, ServerWebExchange exchange) {
//        exchange.getAttributes().putIfAbsent(ERROR_INTERNAL_ATTRIBUTE, error);
//    }
//}
