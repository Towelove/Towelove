package blossom.project.towelove.common.exception.todo;

import blossom.project.towelove.common.exception.AbstractException;
import blossom.project.towelove.common.exception.errorcode.ErrorCode;
import blossom.project.towelove.common.exception.errorcode.IErrorCode;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 16:14 2024/1/19
 */
public class TodoWidgetMaxException extends AbstractException {

    public TodoWidgetMaxException(ToDoErrorCode errorCode) {
        super(errorCode.message(), null, errorCode);
    }

}
