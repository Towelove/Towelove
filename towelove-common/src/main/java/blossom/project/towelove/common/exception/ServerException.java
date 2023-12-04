package blossom.project.towelove.common.exception;


import blossom.project.towelove.common.exception.errorcode.ErrorCode;
import blossom.project.towelove.common.exception.errorcode.IErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务器异常 Exception
 * @author :张锦标
 */
@EqualsAndHashCode(callSuper = true)
public final class ServerException extends AbstractException{

    public ServerException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    public ServerException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

}
