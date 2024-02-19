package blossom.project.towelove.common.exception;

import blossom.project.towelove.common.exception.errorcode.IErrorCode;

/**
 * @author: ZhangBlossom
 * @date: 2023/12/1 12:05
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * EntityNotFoundException类
 */
public class EntityNotFoundException extends AbstractException{

    public EntityNotFoundException(String message, IErrorCode errorCode) {
        super(message, null, errorCode);
    }
    public EntityNotFoundException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

}
