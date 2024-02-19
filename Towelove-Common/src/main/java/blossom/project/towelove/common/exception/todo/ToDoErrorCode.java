package blossom.project.towelove.common.exception.todo;

import blossom.project.towelove.common.exception.errorcode.IErrorCode;

/**
 * @author wangLele
 * @Mail 1819220754@qq.com
 * @date 12:23 2024/1/22
 */
public enum ToDoErrorCode implements IErrorCode {

    WIDGET_MAX_CODE("1003","你可别太贪 只能两个"),
    NOT_FOUND_CODE("1004","母鸡呀~~~~ 没找到")

    ;

    private final String code;

    private final String message;

    ToDoErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
