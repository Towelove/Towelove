package blossom.project.towelove.framework.flower.extension;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExtensionInvoker {
    private static final Logger log = LoggerFactory.getLogger(ExtensionInvoker.class);
    private Object instance;
    private Method method;

    public <T> T invoke(Object... args) throws Throwable {
        try {
            return (T) this.method.invoke(this.instance, args);
        } catch (IllegalAccessException var3) {
            throw new RuntimeException("扩展点执行异常, instanceClass = " + this.instance.getClass() + "method = " + this.method.getName() + "args = " + JSONObject.toJSONString(args), var3);
        } catch (InvocationTargetException var4) {
            throw var4.getTargetException();
        }
    }

    public ExtensionInvoker(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }
}
