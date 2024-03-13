package blossom.project.towelove.framework.flower.executor;

import blossom.project.towelove.framework.flower.model.FlowBizContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
/**
 * @author: ZhangBlossom
 * @date: 2024/1/23 11:22
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 * 通过spring项目启动的时候会去加载xml配置文件这一特性，我们可以把
 * 想要顺序执行的方法名称放入到xml文件中，通过value的方式注入到如下面的类。
 * 然后使用反射的方式，调用这些方法。
 * 也就是在内存中构建  string（方法名称）--- method（bean对象）
 */
@Data
@AllArgsConstructor
public abstract class AbstractFlowTemplate implements ApplicationContextAware {
    /**
     * 前置处理器
     */
    protected List<String> beforeExecutor;
    /**
     * 处理器
     */
    protected List<String> executor;
    /**
     * 后置处理器
     */
    protected List<String> afterExecutor;

    protected boolean executorEnable = true;
    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public abstract void execute(FlowBizContext var1);

    public AbstractFlowTemplate() {
    }

}
