package blossom.project.towelove.framework.redis.lock;

/**
 * @author 张锦标
 */
public class LockException extends Exception {
    private String msg;

    public LockException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
