package blossom.project.towelove.common.exception.todo;

import blossom.project.towelove.common.exception.AbstractException;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 16:48 2024/1/23
 */
public class TodoNotFoundException extends AbstractException {
    public TodoNotFoundException(ToDoErrorCode errorCode) {
        super(errorCode.message(), null, errorCode);
    }
}
