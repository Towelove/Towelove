package blossom.project.towelove.framework.flower.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

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

    public FlowBizContext() {
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    public String getFlowCode() {
        return this.flowCode;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public String getBizTag() {
        return this.bizTag;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public void setBizTag(String bizTag) {
        this.bizTag = bizTag;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FlowBizContext)) {
            return false;
        } else {
            FlowBizContext other = (FlowBizContext)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$variables = this.getVariables();
                    Object other$variables = other.getVariables();
                    if (this$variables == null) {
                        if (other$variables == null) {
                            break label59;
                        }
                    } else if (this$variables.equals(other$variables)) {
                        break label59;
                    }

                    return false;
                }

                Object this$flowCode = this.getFlowCode();
                Object other$flowCode = other.getFlowCode();
                if (this$flowCode == null) {
                    if (other$flowCode != null) {
                        return false;
                    }
                } else if (!this$flowCode.equals(other$flowCode)) {
                    return false;
                }

                Object this$bizCode = this.getBizCode();
                Object other$bizCode = other.getBizCode();
                if (this$bizCode == null) {
                    if (other$bizCode != null) {
                        return false;
                    }
                } else if (!this$bizCode.equals(other$bizCode)) {
                    return false;
                }

                Object this$bizTag = this.getBizTag();
                Object other$bizTag = other.getBizTag();
                if (this$bizTag == null) {
                    if (other$bizTag != null) {
                        return false;
                    }
                } else if (!this$bizTag.equals(other$bizTag)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof FlowBizContext;
    }

    @Override
    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        Object $variables = this.getVariables();
        result = result * 59 + ($variables == null ? 43 : $variables.hashCode());
        Object $flowCode = this.getFlowCode();
        result = result * 59 + ($flowCode == null ? 43 : $flowCode.hashCode());
        Object $bizCode = this.getBizCode();
        result = result * 59 + ($bizCode == null ? 43 : $bizCode.hashCode());
        Object $bizTag = this.getBizTag();
        result = result * 59 + ($bizTag == null ? 43 : $bizTag.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "FlowBizContext(variables=" + this.getVariables() + ", flowCode=" + this.getFlowCode() + ", bizCode=" + this.getBizCode() + ", bizTag=" + this.getBizTag() + ")";
    }
}
