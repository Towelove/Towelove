package blossom.project.towelove.framework.log.handler;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.util.Optional;

/**
 * 抽象项目中三类异常体系，客户端异常、服务端异常以及远程服务调用异常
 *
 * @author zjb
 * @see
 * @see ServiceException
 * @see RemoteException
 */
@Getter
public abstract class AbstractException extends RuntimeException {
    
    public final String errorCode;
    
    public final String errorMessage;
    
    public AbstractException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable);
        this.errorCode = errorCode.code();
        this.errorMessage = Optional.ofNullable(StrUtil.emptyToNull(message)).orElse(errorCode.message());
    }
}
