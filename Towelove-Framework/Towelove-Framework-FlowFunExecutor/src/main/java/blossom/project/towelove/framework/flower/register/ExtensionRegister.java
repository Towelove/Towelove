//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package blossom.project.towelove.framework.flower.register;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import blossom.project.towelove.framework.flower.annotation.Extension;
import blossom.project.towelove.framework.flower.annotation.ExtensionsImpl;
import blossom.project.towelove.framework.flower.enums.TemplateTypeEnum;
import blossom.project.towelove.framework.flower.extension.ExtensionExecutor;
import blossom.project.towelove.framework.flower.extension.ExtensionInterfaceInvocationHandler;
import blossom.project.towelove.framework.flower.extension.ExtensionInvoker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author: ZhangBlossom
 * @date: 2024/3/13 17:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 还在开发ing
 */
@Deprecated
public class ExtensionRegister implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(ExtensionRegister.class);
    private ApplicationContext applicationContext;
    public static final Map<String, ExtensionInvoker> EXTENSION_INVOKER_MAP = new ConcurrentHashMap();
    private static final Map<Class<?>, Object> AGENT_MAP = new ConcurrentHashMap();
    private AtomicBoolean isRegisted = new AtomicBoolean(false);

    public ExtensionRegister() {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.isRegisted.compareAndSet(false, true)) {
            Set<String> extensionCodeSet = new HashSet();
            ExtensionExecutor extensionExecutor = (ExtensionExecutor)this.applicationContext.getBean(ExtensionExecutor.class);
            Map<String, Object> map = this.applicationContext.getBeansWithAnnotation(ExtensionsImpl.class);
            map.forEach((beanName, bean) -> {
                ExtensionsImpl extensionsImpl = (ExtensionsImpl)this.applicationContext.findAnnotationOnBean(beanName, ExtensionsImpl.class);

                assert extensionsImpl != null;

                Integer templateTypeCode = extensionsImpl.templateType().getCode();
                String templateCode;
                if (extensionsImpl.templateType() == TemplateTypeEnum.BIZ) {
                    if (StringUtils.isBlank(extensionsImpl.templateCode())) {
                        throw new RuntimeException("TemplateType为BIZ时,templateCode不能为空");
                    }

                    templateCode = extensionsImpl.templateCode();
                } else {
                    templateCode = "";
                }

                Class<?> clazz = bean.getClass();
                List<Method> interfaceMethodList = new ArrayList();
                Class<?>[] interfaces = clazz.getInterfaces();
                Arrays.stream(interfaces).forEach((interfaceClass) -> {
                    Method[] methods = interfaceClass.getDeclaredMethods();
                    boolean isExtensionGroup = false;
                    Method[] var6 = methods;
                    int var7 = methods.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Method method = var6[var8];
                        Extension extension = (Extension)method.getAnnotation(Extension.class);
                        if (extension != null) {
                            isExtensionGroup = true;
                            if (StringUtils.isAnyBlank(new CharSequence[]{extension.code(), extension.name()})) {
                                throw new RuntimeException("@Extension的code和name不能为空, interfaceClass = " + interfaceClass.getName());
                            }

                            if (!method.isDefault()) {
                                throw new RuntimeException(interfaceClass.getName() + "#" + method.getName() + "必须有default空实现");
                            }

                            extensionCodeSet.add(extension.code());
                            interfaceMethodList.add(method);
                        }
                    }

                    if (isExtensionGroup) {
                        registerAgent(interfaceClass, extensionExecutor);
                    }

                });
                interfaceMethodList.forEach((interfaceMethod) -> {
                    Method method;
                    try {
                        method = clazz.getDeclaredMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());
                    } catch (NoSuchMethodException var9) {
                        return;
                    }

                    Extension extension = (Extension)interfaceMethod.getAnnotation(Extension.class);
                    String key = extension.code() + ":" + templateTypeCode + ":" + templateCode;
                    ExtensionInvoker extensionInvoker = new ExtensionInvoker(bean, method);
                    if (EXTENSION_INVOKER_MAP.putIfAbsent(key, extensionInvoker) != null) {
                        throw new RuntimeException("扩展实现存在冲突，class = " + clazz.getName());
                    }
                });
            });
            extensionCodeSet.forEach((code) -> {
                String key = code + ":" + TemplateTypeEnum.DEFAULT.getCode() + ":";
                if (!EXTENSION_INVOKER_MAP.containsKey(key)) {
                    throw new RuntimeException("扩展点缺少中台默认实现, code = " + code);
                }
            });
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static void registerAgent(Class<?> clazz, ExtensionExecutor extensionExecutor) {
        if (clazz != null && clazz.isInterface()) {
            if (!AGENT_MAP.containsKey(clazz)) {
                Object proxyInstance = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ExtensionInterfaceInvocationHandler(extensionExecutor));
                AGENT_MAP.put(clazz, proxyInstance);
            }
        } else {
            throw new RuntimeException("扩展点必须是接口");
        }
    }

    public static <T> Object getAgent(Class<T> clazz) {
        return AGENT_MAP.get(clazz);
    }
}
