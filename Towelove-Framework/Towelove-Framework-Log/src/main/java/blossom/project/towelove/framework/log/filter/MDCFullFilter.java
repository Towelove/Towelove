//package blossom.project.towelove.framework.log.filter;
//
//import blossom.project.towelove.framework.log.aspect.LoveLogAspect;
//import blossom.project.towelove.framework.log.handler.SecurityConstant;
//import cn.hutool.core.util.StrUtil;
//import org.slf4j.MDC;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.context.request.WebRequestInterceptor;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.logging.Filter;
//import java.util.logging.LogRecord;
//
///**
// * @projectName: Towelove
// * @package: blossom.project.towelove.framework.log.filter
// * @className: MDCFullFilter
// * @author: Link Ji
// * @description: GOGO
// * @date: 2024/4/1 18:14
// * @version: 1.0
// */
//public class MDCFullFilter implements HandlerInterceptor {
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        String requestId = MDC.get(SecurityConstant.REQUEST_ID);
//        if (StrUtil.isNotBlank(requestId)){
//            //在响应中加上RequestId
//            LoveLogAspect.Result<?> result = (LoveLogAspect.Result<?>) modelAndView.getModel().get("result");
//            result.setRequestId(requestId);
//        }
//        //清除MDC缓存，防止内存泄露
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        MDC.clear();
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
//}
