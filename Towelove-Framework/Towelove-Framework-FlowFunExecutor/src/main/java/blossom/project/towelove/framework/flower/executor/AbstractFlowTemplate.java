package blossom.project.towelove.framework.flower.executor;

import blossom.project.towelove.framework.flower.model.FlowBizContext;
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
public abstract class AbstractFlowTemplate implements ApplicationContextAware {
    protected List<String> beforeTrans;
    protected List<String> trans;
    protected List<String> afterTrans;
    protected boolean transEnable = true;
    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public abstract void execute(FlowBizContext var1);

    public AbstractFlowTemplate() {
    }

    public List<String> getBeforeTrans() {
        return this.beforeTrans;
    }

    public List<String> getTrans() {
        return this.trans;
    }

    public List<String> getAfterTrans() {
        return this.afterTrans;
    }

    public boolean isTransEnable() {
        return this.transEnable;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setBeforeTrans(List<String> beforeTrans) {
        this.beforeTrans = beforeTrans;
    }

    public void setTrans(List<String> trans) {
        this.trans = trans;
    }

    public void setAfterTrans(List<String> afterTrans) {
        this.afterTrans = afterTrans;
    }

    public void setTransEnable(boolean transEnable) {
        this.transEnable = transEnable;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AbstractFlowTemplate)) {
            return false;
        } else {
            AbstractFlowTemplate other = (AbstractFlowTemplate)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isTransEnable() != other.isTransEnable()) {
                return false;
            } else {
                label61: {
                    Object this$beforeTrans = this.getBeforeTrans();
                    Object other$beforeTrans = other.getBeforeTrans();
                    if (this$beforeTrans == null) {
                        if (other$beforeTrans == null) {
                            break label61;
                        }
                    } else if (this$beforeTrans.equals(other$beforeTrans)) {
                        break label61;
                    }

                    return false;
                }

                label54: {
                    Object this$trans = this.getTrans();
                    Object other$trans = other.getTrans();
                    if (this$trans == null) {
                        if (other$trans == null) {
                            break label54;
                        }
                    } else if (this$trans.equals(other$trans)) {
                        break label54;
                    }

                    return false;
                }

                Object this$afterTrans = this.getAfterTrans();
                Object other$afterTrans = other.getAfterTrans();
                if (this$afterTrans == null) {
                    if (other$afterTrans != null) {
                        return false;
                    }
                } else if (!this$afterTrans.equals(other$afterTrans)) {
                    return false;
                }

                Object this$applicationContext = this.getApplicationContext();
                Object other$applicationContext = other.getApplicationContext();
                if (this$applicationContext == null) {
                    if (other$applicationContext != null) {
                        return false;
                    }
                } else if (!this$applicationContext.equals(other$applicationContext)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof AbstractFlowTemplate;
    }

    @Override
    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        result = result * 59 + (this.isTransEnable() ? 79 : 97);
        Object $beforeTrans = this.getBeforeTrans();
        result = result * 59 + ($beforeTrans == null ? 43 : $beforeTrans.hashCode());
        Object $trans = this.getTrans();
        result = result * 59 + ($trans == null ? 43 : $trans.hashCode());
        Object $afterTrans = this.getAfterTrans();
        result = result * 59 + ($afterTrans == null ? 43 : $afterTrans.hashCode());
        Object $applicationContext = this.getApplicationContext();
        result = result * 59 + ($applicationContext == null ? 43 : $applicationContext.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "AbstractFlowTemplate(beforeTrans=" + this.getBeforeTrans() + ", trans=" + this.getTrans() + ", afterTrans=" + this.getAfterTrans() + ", transEnable=" + this.isTransEnable() + ", applicationContext=" + this.getApplicationContext() + ")";
    }
}
