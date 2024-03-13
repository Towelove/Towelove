//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package blossom.project.towelove.framework.flower.extension;


import blossom.project.towelove.framework.flower.register.ExtensionRegister;

public class ExtensionHelper {
    public ExtensionHelper() {
    }

    public static <T> T getExtensionGroup(Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException();
        } else {
            Object agentHandler = ExtensionRegister.getAgent(clazz);
            if (agentHandler == null) {
                throw new RuntimeException("该类没有扩展点实现" + clazz.getName());
            } else {
                return (T) agentHandler;
            }
        }
    }
}
