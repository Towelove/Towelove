package blossom.project.towelove.framework.flower.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;



/**
 * @author: ZhangBlossom
 * @date: 2024/3/13 14:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 流水线执行业务上下文对象
 * 标识当前要执行的方法的类型/编码/业务标志
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FlowBizContext {
    protected Map<String, Object> variables = new ConcurrentHashMap();
    protected String flowCode;
    protected String bizCode;
    protected String bizTag;

    public void setVariable(String key, Object value) {
        if (!StringUtils.isBlank(key) && value != null) {
            this.variables.put(key, value);
        }
    }

    public <T> T getVariable(String key) {
        return StringUtils.isBlank(key) ? null : (T) this.variables.get(key);
    }

    public <T> T getVariableWithInit(String key, Supplier<T> onMissingInit) {
        T t = this.getVariable(key);
        if (t == null && onMissingInit != null) {
            t = onMissingInit.get();
            this.setVariable(key, t);
        }

        return t;
    }


}
