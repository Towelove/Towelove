package blossom.project.towelove.framework.web.handler;

import blossom.project.towelove.common.constant.SecurityConstants;
import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.common.response.AjaxResult;
import blossom.project.towelove.common.response.Result;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public AjaxResult SystemException(Exception e){
        return AjaxResult.error(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceException.class)
    public Result<String> SystemException(ServiceException e){
        return Result.fail(e.getMessage(), SecurityConstants.REQUEST_ID);
    }
}
