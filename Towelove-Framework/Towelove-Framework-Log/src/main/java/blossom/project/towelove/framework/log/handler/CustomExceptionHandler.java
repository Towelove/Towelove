package blossom.project.towelove.framework.log.handler;

import blossom.project.towelove.common.constant.SecurityConstant;
import blossom.project.towelove.common.exception.RemoteException;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.response.Result;
import com.alibaba.fastjson2.JSON;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import lombok.extern.slf4j.Slf4j;



/**
 * 全局异常处理
 *
 * @author 张锦标
 * @date 2023-08-16
 */
@Slf4j
@AutoConfiguration
@ControllerAdvice
public class CustomExceptionHandler {


    /**
     * 全局处理{@link Exception}
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handleException(Exception ex) {
        log.warn("[Handled] Exception ", ex);
        return Result.fail(ex.getCause(),500,
                ex.getMessage(), MDC.get(SecurityConstant.REQUEST_ID));
    }


    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public String handleServiceException(ServiceException serviceException){
        log.error("[Service] Exception",serviceException);
        return JSON.toJSONString(Result.fail(serviceException.getDetailMessage()
                ,500,serviceException.getMessage(),MDC.get(SecurityConstant.REQUEST_ID)));
    }


    /**
     * 全局处理{@link RemoteException}
     */
    @ExceptionHandler(value = RemoteException.class)
    @ResponseBody
    public Object handleRemoteException(RemoteException ex) {
        log.warn("[Handled] RemoteException ", ex);
        return Result.fail(ex.getCause(),Integer.parseInt(ex.getErrorCode()),
                ex.getMessage(),MDC.get(SecurityConstant.REQUEST_ID));
    }


    /**
     * 拼接所有验证error message
     */
    private String listAllErrors(List<ObjectError> allErrors) {
        StringBuilder errorMsg = new StringBuilder();
        allErrors.forEach(e -> errorMsg.append(e.getDefaultMessage()).append(" |"));
        return errorMsg.toString().substring(0, errorMsg.length() - 2);
    }


}
