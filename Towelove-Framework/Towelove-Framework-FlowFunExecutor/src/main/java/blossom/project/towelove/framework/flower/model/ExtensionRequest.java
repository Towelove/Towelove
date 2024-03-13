package blossom.project.towelove.framework.flower.model;


/**
 * @author: ZhangBlossom
 * @date: 2024/3/12 14:28
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * 还在开发ing
 */
@Deprecated
public class ExtensionRequest {
    private String extensionCode;
    private String bizCode;

    public ExtensionRequest() {
    }

    public String getExtensionCode() {
        return this.extensionCode;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ExtensionRequest)) {
            return false;
        } else {
            ExtensionRequest other = (ExtensionRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$extensionCode = this.getExtensionCode();
                Object other$extensionCode = other.getExtensionCode();
                if (this$extensionCode == null) {
                    if (other$extensionCode != null) {
                        return false;
                    }
                } else if (!this$extensionCode.equals(other$extensionCode)) {
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

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ExtensionRequest;
    }

    @Override
    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        Object $extensionCode = this.getExtensionCode();
        result = result * 59 + ($extensionCode == null ? 43 : $extensionCode.hashCode());
        Object $bizCode = this.getBizCode();
        result = result * 59 + ($bizCode == null ? 43 : $bizCode.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ExtensionRequest(extensionCode=" + this.getExtensionCode() + ", bizCode=" + this.getBizCode() + ")";
    }
}
