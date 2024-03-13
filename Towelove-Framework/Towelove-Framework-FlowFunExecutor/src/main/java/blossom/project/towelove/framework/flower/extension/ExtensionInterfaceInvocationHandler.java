//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package blossom.project.towelove.framework.flower.extension;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import blossom.project.towelove.framework.flower.annotation.Extension;
import blossom.project.towelove.framework.flower.model.ExtensionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtensionInterfaceInvocationHandler implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(ExtensionInterfaceInvocationHandler.class);
    private final ExtensionExecutor extensionExecutor;

    public ExtensionInterfaceInvocationHandler(ExtensionExecutor extensionExecutor) {
        this.extensionExecutor = extensionExecutor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Extension extension = (Extension)method.getAnnotation(Extension.class);
        if (args != null && args[0] instanceof ExtensionRequest && extension != null) {
            ExtensionRequest extensionRequests = (ExtensionRequest)args[0];
            extensionRequests.setExtensionCode(extension.code());
            return this.extensionExecutor.execute((ExtensionRequest)args[0]);
        } else {
            log.warn("调用了非扩展点的方法, method = {}", method.getName());
            return null;
        }
    }
}
