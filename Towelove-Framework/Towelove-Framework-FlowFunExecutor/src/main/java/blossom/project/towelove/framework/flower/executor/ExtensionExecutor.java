package blossom.project.towelove.framework.flower.executor;

import blossom.project.towelove.framework.flower.enums.TemplateTypeEnum;
import blossom.project.towelove.framework.flower.model.ExtensionRequest;
import blossom.project.towelove.framework.flower.register.ExtensionRegister;
import org.apache.commons.lang3.StringUtils;


public class ExtensionExecutor {
    public ExtensionExecutor() {
    }

    public <T> T execute(ExtensionRequest request) throws Throwable {
        return this.getInvoker(request.getExtensionCode(), request.getBizCode()).invoke(new Object[]{request});
    }

    private ExtensionInvoker getInvoker(String extensionCode, String bizCode) {
        if (StringUtils.isBlank(extensionCode)) {
            throw new RuntimeException("执行扩展点时,扩展点编码不能为空");
        } else if (StringUtils.isBlank(bizCode)) {
            throw new RuntimeException("执行扩展点时,业务身份不能为空");
        } else {
            String key = extensionCode + ":" + TemplateTypeEnum.BIZ.getCode() + ":" + bizCode;
            ExtensionInvoker extensionInvoker = (ExtensionInvoker) ExtensionRegister.EXTENSION_INVOKER_MAP.get(key);
            if (extensionInvoker == null) {
                key = extensionCode + ":" + TemplateTypeEnum.DEFAULT.getCode() + ":";
                extensionInvoker = (ExtensionInvoker)ExtensionRegister.EXTENSION_INVOKER_MAP.get(key);
            }

            if (extensionInvoker == null) {
                throw new RuntimeException("执行扩展点时找不到扩展点的实现类, extensionCode = " + extensionCode + ", bizCode " + bizCode);
            } else {
                return extensionInvoker;
            }
        }
    }
}
