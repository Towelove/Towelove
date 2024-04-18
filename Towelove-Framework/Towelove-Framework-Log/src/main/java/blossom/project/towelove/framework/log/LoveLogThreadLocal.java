package blossom.project.towelove.framework.log;


/**
 * @author: 张锦标
 * @date: 2024/4/18 5:32 PM
 * LoveLogThreadLocal
 */

public class LoveLogThreadLocal {
    public static final InheritableThreadLocal<LoveLogContext> REQUEST_THREAD_LOCAL = new InheritableThreadLocal();

    public LoveLogThreadLocal() {
    }

    public static LoveLogContext init() {
        if (REQUEST_THREAD_LOCAL.get() == null) {
            LoveLogContext loveLogContext = new LoveLogContext();
            REQUEST_THREAD_LOCAL.set(loveLogContext);
        }

        return (LoveLogContext)REQUEST_THREAD_LOCAL.get();
    }

    public static LoveLogContext get() {
        return (LoveLogContext)REQUEST_THREAD_LOCAL.get();
    }

    public static void clear() {
        REQUEST_THREAD_LOCAL.remove();
    }
}
